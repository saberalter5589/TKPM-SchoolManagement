package school.management.school_management_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.management.school_management_be.common.UserRole;
import school.management.school_management_be.dto.request.sclass.CreateClassRequest;
import school.management.school_management_be.dto.request.score.CreateScoreRequest;
import school.management.school_management_be.dto.request.score.GetScoreRequest;
import school.management.school_management_be.dto.request.subject.GetSubjectRequest;
import school.management.school_management_be.dto.response.SuccessResponse;
import school.management.school_management_be.dto.response.sclass.CreateClassResponse;
import school.management.school_management_be.dto.response.score.GetScoreResponse;
import school.management.school_management_be.dto.response.subject.GetSubjectResponse;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.service.ScoreService;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class ScoreController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/score/create")
    public ResponseEntity<SuccessResponse> addScore(@RequestBody CreateScoreRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        scoreService.createScore(request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @GetMapping("/score/search")
    public ResponseEntity<GetScoreResponse> searchScore(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value="subjectId", required = false) Long subjectId,
            @RequestParam(value="semester", required = false) Long semester){
        GetScoreRequest request = new GetScoreRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setStudentId(studentId);
        request.setSubjectId(subjectId);
        request.setSemester(semester);
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        GetScoreResponse response = scoreService.getScores(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
