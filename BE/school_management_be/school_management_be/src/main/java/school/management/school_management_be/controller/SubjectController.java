package school.management.school_management_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.management.school_management_be.common.UserRole;
import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.classtype.CreateClassTypeRequest;
import school.management.school_management_be.dto.request.classtype.GetClassTypeRequest;
import school.management.school_management_be.dto.request.subject.CreateSubjectRequest;
import school.management.school_management_be.dto.request.subject.GetSubjectRequest;
import school.management.school_management_be.dto.request.user.CreateUserRequest;
import school.management.school_management_be.dto.response.SuccessResponse;
import school.management.school_management_be.dto.response.classtype.GetClassTypeResponse;
import school.management.school_management_be.dto.response.subject.CreateSubjectResponse;
import school.management.school_management_be.dto.response.subject.GetSubjectResponse;
import school.management.school_management_be.dto.response.user.CreateUserResponse;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.service.SubjectService;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class SubjectController {
    @Autowired
    SubjectService subjectService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/subject/create")
    public ResponseEntity<CreateSubjectResponse> addSubject(@RequestBody CreateSubjectRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        CreateSubjectResponse response = subjectService.createSubject(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/subject/search")
    public ResponseEntity<GetSubjectResponse> searchSubject(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "subjectId", required = false) Long subjectId,
            @RequestParam(value="subjectCode", required = false) String subjectCode,
            @RequestParam(value="subjectName", required = false) String subjectName){
        GetSubjectRequest request = new GetSubjectRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setSubjectId(subjectId);
        request.setSubjectCode(subjectCode);
        request.setSubjectName(subjectName);
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        GetSubjectResponse response = subjectService.searchSubject(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/subject/{id}")
    public ResponseEntity<SuccessResponse> editClassType(@PathVariable("id") Long id, @RequestBody CreateSubjectRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        subjectService.updateSubject(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/subject/{id}")
    public ResponseEntity<SuccessResponse> deleteUser(@PathVariable("id") Long id,@RequestHeader("userId") Long userId,
                                                      @RequestHeader("password") String password){
        BaseRequest request = new BaseRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        subjectService.deleteSubject(id);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}


