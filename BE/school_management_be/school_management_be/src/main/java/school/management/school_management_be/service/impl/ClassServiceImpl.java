package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.management.school_management_be.dto.obj.AuthDto;
import school.management.school_management_be.dto.obj.ClassDto;
import school.management.school_management_be.dto.obj.ClassTypeDto;
import school.management.school_management_be.dto.obj.MessageInfo;
import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.sclass.CreateClassRequest;
import school.management.school_management_be.dto.request.sclass.GetClassRequest;
import school.management.school_management_be.dto.response.sclass.CreateClassResponse;
import school.management.school_management_be.dto.response.sclass.GetClassResponse;
import school.management.school_management_be.entity.SClass;
import school.management.school_management_be.exception.NoDataFoundException;
import school.management.school_management_be.mapper.ClassDxo;
import school.management.school_management_be.mapper.ClassTypeDxo;
import school.management.school_management_be.mapper.EntityDxo;
import school.management.school_management_be.repository.ClassRepository;
import school.management.school_management_be.repository.SubjectRepository;
import school.management.school_management_be.service.ClassService;
import school.management.school_management_be.util.MessageUtil;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassRepository classRepository;

    @Override
    @Transactional
    public CreateClassResponse createClass(CreateClassRequest request) {
        Long authId = request.getAuthentication().getUserId();

        SClass sClass = new SClass();
        sClass.setClassTypeId(request.getClassTypeId());
        sClass.setClassCode(request.getClassCode());
        sClass.setClassName(request.getClassName());
        sClass.setMaxStudentNum(request.getMaxStudentNum());
        sClass.setDescription(request.getDescription());
        sClass.setNote(request.getNote());
        sClass.setIsDeleted(false);
        EntityDxo.preCreate(authId, sClass);
        classRepository.save(sClass);

        CreateClassResponse response = new CreateClassResponse();
        ClassDto classDto = new ClassDto();
        classDto.setClassId(sClass.getClassId());
        response.setClassDto(classDto);
        return response;
    }

    @Override
    public GetClassResponse searchClass(GetClassRequest request) {
        List<Object[]> dbList = classRepository.searchClass(request);
        List<ClassDto> dtoList = ClassDxo.mapFromDbObjListToClassDtoList(dbList);
        GetClassResponse response = new GetClassResponse();
        response.setClassDtoList(dtoList);
        return response;
    }

    @Override
    @Transactional
    public void updateClass(Long id, CreateClassRequest request) {
        Long authId = request.getAuthentication().getUserId();
        SClass sClass = classRepository.findByClassId(id);
        if(sClass == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "classId");
            throw new NoDataFoundException(messageInfo);
        }
        sClass.setClassTypeId(request.getClassTypeId());
        sClass.setClassCode(request.getClassCode());
        sClass.setClassName(request.getClassName());
        sClass.setMaxStudentNum(request.getMaxStudentNum());
        sClass.setDescription(request.getDescription());
        sClass.setNote(request.getNote());
        EntityDxo.preUpdate(authId, sClass);
        classRepository.save(sClass);
    }

    @Override
    @Transactional
    public void deleteClass(Long id, BaseRequest request) {
        Long authId = request.getAuthentication().getUserId();
        SClass sClass = classRepository.findByClassId(id);
        if(sClass == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "classId");
            throw new NoDataFoundException(messageInfo);
        }
        sClass.setIsDeleted(true);
        EntityDxo.preUpdate(authId, sClass);
        classRepository.save(sClass);
    }
}
