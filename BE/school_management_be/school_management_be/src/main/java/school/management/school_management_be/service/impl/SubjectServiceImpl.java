package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.management.school_management_be.dto.obj.ClassTypeDto;
import school.management.school_management_be.dto.obj.MessageInfo;
import school.management.school_management_be.dto.obj.SubjectDto;
import school.management.school_management_be.dto.request.subject.CreateSubjectRequest;
import school.management.school_management_be.dto.request.subject.GetSubjectRequest;
import school.management.school_management_be.dto.response.subject.CreateSubjectResponse;
import school.management.school_management_be.dto.response.subject.GetSubjectResponse;
import school.management.school_management_be.entity.ClassType;
import school.management.school_management_be.entity.Subject;
import school.management.school_management_be.exception.NoDataFoundException;
import school.management.school_management_be.mapper.ClassTypeDxo;
import school.management.school_management_be.mapper.EntityDxo;
import school.management.school_management_be.mapper.SubjectDxo;
import school.management.school_management_be.repository.SubjectRepository;
import school.management.school_management_be.service.SubjectService;
import school.management.school_management_be.util.MessageUtil;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    @Transactional
    public CreateSubjectResponse createSubject(CreateSubjectRequest request) {
        Subject subject = new Subject();
        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setAvgScore(request.getAvgScore());
        subject.setDescription(request.getDescription());
        subject.setNote(request.getNote());
        subject.setIsDeleted(false);
        EntityDxo.preCreate(0L, subject);
        subjectRepository.save(subject);

        SubjectDto dto = new SubjectDto();
        dto.setSubjectId(subject.getSubjectId());
        CreateSubjectResponse response = new CreateSubjectResponse();
        response.setSubjectDto(dto);
        return response;
    }

    @Override
    public GetSubjectResponse searchSubject(GetSubjectRequest request) {
        List<Object[]> dbList = subjectRepository.searchSubject(request);
        List<SubjectDto> dtoList = SubjectDxo.mapFromDbObjListToSubjectDtoList(dbList);
        GetSubjectResponse response = new GetSubjectResponse();
        response.setSubjectDtoList(dtoList);
        return response;
    }

    @Override
    @Transactional
    public void updateSubject(Long id, CreateSubjectRequest request) {
        Subject subject = subjectRepository.findBySubjectId(id);
        if(subject == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "subjectId");
            throw new NoDataFoundException(messageInfo);
        }
        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setAvgScore(request.getAvgScore());
        subject.setDescription(request.getDescription());
        subject.setNote(request.getNote());
        EntityDxo.preUpdate(0L, subject);
        subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findBySubjectId(id);
        if(subject == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "subjectId");
            throw new NoDataFoundException(messageInfo);
        }
        subject.setIsDeleted(true);
        EntityDxo.preUpdate(0L, subject);
        subjectRepository.save(subject);
    }
}
