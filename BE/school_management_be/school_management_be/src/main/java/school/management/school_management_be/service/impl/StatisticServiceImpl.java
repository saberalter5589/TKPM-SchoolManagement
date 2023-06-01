package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.school_management_be.dto.obj.StudentSemesterScoreStatisticDto;
import school.management.school_management_be.dto.obj.StudentSubjectScoreStatisticDto;
import school.management.school_management_be.dto.request.statistic.GetClassStatisticRequest;
import school.management.school_management_be.dto.request.statistic.GetStudentStatisticRequest;
import school.management.school_management_be.dto.response.statistic.GetClassStatisticResponse;
import school.management.school_management_be.dto.response.statistic.GetStudentStatisticResponse;
import school.management.school_management_be.entity.SClass;
import school.management.school_management_be.entity.Student;
import school.management.school_management_be.mapper.StatisticDxo;
import school.management.school_management_be.repository.ClassRepository;
import school.management.school_management_be.repository.ScoreRepository;
import school.management.school_management_be.repository.StudentRepository;
import school.management.school_management_be.service.StatisticService;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ClassRepository classRepository;


    @Override
    public GetStudentStatisticResponse getStudentSubjectStatistic(GetStudentStatisticRequest request) {
        Long studentId = request.getStudentId();

        List<Object[]> dbList = scoreRepository.getStudentSubjectStatistic(studentId);
        List<StudentSubjectScoreStatisticDto> dtoList = StatisticDxo.mapFromDbObjListToStudentSubjectDtoList(dbList);

        GetStudentStatisticResponse response = new GetStudentStatisticResponse();
        response.setSubjectDtoList(dtoList);

        Student student = studentRepository.findByStudentId(studentId);
        SClass sClass = classRepository.findByClassId(student.getClassId());
        response.setStudentName(student.getFirstName() + " " + student.getLastName());
        response.setClassName(sClass.getClassName());

        return response;
    }

    @Override
    public GetClassStatisticResponse getClassStatistic(GetClassStatisticRequest request) {
        Long classId = request.getClassId();
        List<Object[]> dbList = scoreRepository.getClassStatistic(classId);
        List<StudentSemesterScoreStatisticDto> dto = StatisticDxo.mapFromDbObjListToStudentSemesterDtoList(dbList);
        GetClassStatisticResponse response = new GetClassStatisticResponse();
        response.setSemesterDtoList(dto);
        return response;
    }
}
