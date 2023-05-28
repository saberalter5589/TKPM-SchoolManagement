package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.management.school_management_be.common.DateTimeFormat;
import school.management.school_management_be.dto.obj.MessageInfo;
import school.management.school_management_be.dto.obj.StudentDto;
import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.student.CreateStudentRequest;
import school.management.school_management_be.dto.request.student.GetStudentRequest;
import school.management.school_management_be.dto.response.student.CreateStudentResponse;
import school.management.school_management_be.dto.response.student.GetStudentResponse;
import school.management.school_management_be.entity.Student;
import school.management.school_management_be.exception.NoDataFoundException;
import school.management.school_management_be.mapper.EntityDxo;
import school.management.school_management_be.mapper.StudentDxo;
import school.management.school_management_be.repository.StudentRepository;
import school.management.school_management_be.service.StudentService;
import school.management.school_management_be.util.DateTimeUtil;
import school.management.school_management_be.util.MessageUtil;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    @Transactional
    public CreateStudentResponse createStudent(CreateStudentRequest request) {
        Long authId = request.getAuthentication().getUserId();

        Student student = new Student();
        // TODO: check class exist and class maxnumstudent
        // TODO: check birthday in range
        student.setClassId(request.getClassId());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setBirthDate(DateTimeUtil.parseStringToDate(request.getBirthDate(), DateTimeFormat.DATE));
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setEmail(request.getEmail());
        student.setDescription(request.getDescription());
        student.setNote(request.getNote());
        student.setIsDeleted(false);
        EntityDxo.preCreate(authId, student);
        studentRepository.save(student);

        return new CreateStudentResponse();
    }

    @Override
    public GetStudentResponse searchStudent(GetStudentRequest request) {
        List<Object[]> dbList = studentRepository.searchStudent(request);
        List<StudentDto> dtoList = StudentDxo.mapFromDbObjListToDtoList(dbList);
        GetStudentResponse response = new GetStudentResponse();
        response.setStudentDtoList(dtoList);
        return response;
    }

    @Override
    @Transactional
    public void updateStudent(Long id, CreateStudentRequest request) {
        Long authId = request.getAuthentication().getUserId();
        Student student = studentRepository.findByStudentId(id);
        if(student == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "studentId");
            throw new NoDataFoundException(messageInfo);
        }
        student.setClassId(request.getClassId());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setBirthDate(DateTimeUtil.parseStringToDate(request.getBirthDate(), DateTimeFormat.DATE));
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setEmail(request.getEmail());
        student.setDescription(request.getDescription());
        student.setNote(request.getNote());
        EntityDxo.preUpdate(authId, student);
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id, BaseRequest request) {
        Long authId = request.getAuthentication().getUserId();
        Student student = studentRepository.findByStudentId(id);
        if(student == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "studentId");
            throw new NoDataFoundException(messageInfo);
        }
        student.setIsDeleted(true);
        EntityDxo.preUpdate(authId, student);
        studentRepository.save(student);
    }
}
