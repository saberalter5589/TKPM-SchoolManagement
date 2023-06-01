package school.management.school_management_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import school.management.school_management_be.common.UserRole;
import school.management.school_management_be.dto.request.statistic.GetClassStatisticRequest;
import school.management.school_management_be.dto.request.statistic.GetStudentStatisticRequest;
import school.management.school_management_be.dto.request.student.GetStudentRequest;
import school.management.school_management_be.dto.response.statistic.GetClassStatisticResponse;
import school.management.school_management_be.dto.response.statistic.GetStudentStatisticResponse;
import school.management.school_management_be.dto.response.student.GetStudentResponse;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.service.StatisticService;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class StatisticController {
    @Autowired
    StatisticService statisticService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/statistic/student-subject")
    public ResponseEntity<GetStudentStatisticResponse> getStudentStat(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "studentId", required = false) Long studentId){
        GetStudentStatisticRequest request = new GetStudentStatisticRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setStudentId(studentId);
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        GetStudentStatisticResponse response = statisticService.getStudentSubjectStatistic(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/statistic/class")
    public ResponseEntity<GetClassStatisticResponse> getClassStat(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "classId", required = false) Long classId){
        GetClassStatisticRequest request = new GetClassStatisticRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setClassId(classId);
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        GetClassStatisticResponse response = statisticService.getClassStatistic(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
