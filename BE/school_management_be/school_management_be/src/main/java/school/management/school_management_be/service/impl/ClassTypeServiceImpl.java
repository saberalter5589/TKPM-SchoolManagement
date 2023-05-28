package school.management.school_management_be.service.impl;

import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.management.school_management_be.dto.obj.ClassTypeDto;
import school.management.school_management_be.dto.obj.MessageInfo;
import school.management.school_management_be.dto.request.classtype.CreateClassTypeRequest;
import school.management.school_management_be.dto.request.classtype.GetClassTypeRequest;
import school.management.school_management_be.dto.response.classtype.CreateClassTypeResponse;
import school.management.school_management_be.dto.response.classtype.GetClassTypeResponse;
import school.management.school_management_be.entity.ClassType;
import school.management.school_management_be.entity.SUser;
import school.management.school_management_be.exception.DataDuplicatedException;
import school.management.school_management_be.exception.NoDataFoundException;
import school.management.school_management_be.mapper.ClassTypeDxo;
import school.management.school_management_be.mapper.EntityDxo;
import school.management.school_management_be.repository.ClassTypeRepository;
import school.management.school_management_be.service.ClassTypeService;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.MessageUtil;

import java.util.HashMap;
import java.util.List;

@Service
public class ClassTypeServiceImpl implements ClassTypeService {
    @Autowired
    ClassTypeRepository classTypeRepository;

    @Override
    @Transactional
    public CreateClassTypeResponse createClassType(CreateClassTypeRequest request) {
        ClassType newClassType = new ClassType();
        newClassType.setClassIndex(request.getClassIndex());
        newClassType.setClassTypeCode(request.getClassTypeCode());
        newClassType.setClassTypeName(request.getClassTypeName());
        newClassType.setDescription(request.getDescription());
        newClassType.setNote(request.getNote());
        newClassType.setIsDeleted(false);
        EntityDxo.preCreate(0L, newClassType);
        classTypeRepository.save(newClassType);

        CreateClassTypeResponse response = new CreateClassTypeResponse();
        ClassTypeDto dto = new ClassTypeDto();
        dto.setClassTypeId(newClassType.getClassTypeId());
        response.setClassTypeDto(dto);
        return response;
    }

    @Override
    public GetClassTypeResponse searchClassType(GetClassTypeRequest request) {
        List<Object[]> dbPartnerList = classTypeRepository.searchClassType(request);
        List<ClassTypeDto> classTypeDtoList = ClassTypeDxo.mapFromDbObjListToClassTypeDtoList(dbPartnerList);
        GetClassTypeResponse response = new GetClassTypeResponse();
        response.setClassTypeDtoList(classTypeDtoList);
        return response;
    }

    @Override
    @Transactional
    public void updateClassType(Long id, CreateClassTypeRequest request) {
        ClassType classType = classTypeRepository.findByClassTypeId(id);
        if(classType == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "classTypeId");
            throw new NoDataFoundException(messageInfo);
        }

        classType.setClassIndex(request.getClassIndex());
        classType.setClassTypeCode(request.getClassTypeCode());
        classType.setClassTypeName(request.getClassTypeName());
        classType.setDescription(request.getDescription());
        classType.setNote(request.getNote());
        EntityDxo.preUpdate(0L, classType);
        classTypeRepository.save(classType);
    }

    @Override
    @Transactional
    public void deleteClassType(Long id) {
        ClassType classType = classTypeRepository.findByClassTypeId(id);
        if(classType == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "classTypeId");
            throw new NoDataFoundException(messageInfo);
        }
        classType.setIsDeleted(true);
        EntityDxo.preUpdate(0L, classType);
        classTypeRepository.save(classType);
    }
}
