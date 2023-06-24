package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.school_management_be.dto.obj.StudentRankStatisticDto;
import school.management.school_management_be.dto.obj.StudentSemesterScoreStatisticDto;
import school.management.school_management_be.dto.obj.StudentSubjectScoreStatisticDto;
import school.management.school_management_be.dto.request.statistic.GetClassStatisticRequest;
import school.management.school_management_be.dto.request.statistic.GetStudentStatisticRequest;
import school.management.school_management_be.dto.response.statistic.GetClassStatisticResponse;
import school.management.school_management_be.dto.response.statistic.GetStudentStatisticResponse;
import school.management.school_management_be.entity.Rule;
import school.management.school_management_be.entity.SClass;
import school.management.school_management_be.entity.Student;
import school.management.school_management_be.mapper.StatisticDxo;
import school.management.school_management_be.repository.ClassRepository;
import school.management.school_management_be.repository.RuleRepository;
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
    @Autowired
    RuleRepository ruleRepository;

    @Override
    public GetStudentStatisticResponse getStudentSubjectStatistic(GetStudentStatisticRequest request) {
        GetStudentStatisticResponse response = new GetStudentStatisticResponse();

        Long studentId = request.getStudentId();
        Student student = studentRepository.findByStudentId(studentId);
        if(student == null){
            return response;
        }

        List<Object[]> dbList = scoreRepository.getStudentSubjectStatistic(studentId);
        List<StudentSubjectScoreStatisticDto> dtoList = StatisticDxo.mapFromDbObjListToStudentSubjectDtoList(dbList);
        response.setSubjectDtoList(dtoList);

        SClass sClass = classRepository.findByClassId(student.getClassId());
        response.setStudentName(student.getFirstName() + " " + student.getLastName());
        response.setClassName(sClass.getClassName());

        List<Rule> ruleList = ruleRepository.findAll();
        Rule rule = ruleList.get(0);
        List<Object[]> dbClassStatList = scoreRepository.getClassStatistic(student.getClassId() ,rule);
        List<StudentSemesterScoreStatisticDto> dtoClassList = StatisticDxo.mapFromDbObjListToStudentSemesterDtoList(dbClassStatList);
        StudentSemesterScoreStatisticDto dto = dtoClassList.stream().filter(d -> d.getStudentId().equals(studentId)).findFirst().get();
        response.setStudentSemesterScoreStatisticDto(dto);

        return response;
    }

    @Override
    public GetClassStatisticResponse getClassStatistic(GetClassStatisticRequest request) {
        Long classId = request.getClassId();
        List<Rule> ruleList = ruleRepository.findAll();
        Rule rule = ruleList.get(0);

        List<Object[]> dbList = scoreRepository.getClassStatistic(classId ,rule);
        List<StudentSemesterScoreStatisticDto> dto = StatisticDxo.mapFromDbObjListToStudentSemesterDtoList(dbList);

        List<Object[]> dbRankList = scoreRepository.getClassRankStatistic(classId,rule);
        Long studentCount = studentRepository.getStudentCountByClass(classId);
        StudentRankStatisticDto rankStatisticDto = StatisticDxo.convertToDto(studentCount, dbRankList);

        GetClassStatisticResponse response = new GetClassStatisticResponse();
        response.setStudentRankStatisticDto(rankStatisticDto);
        response.setSemesterDtoList(dto);
        return response;
    }
}
