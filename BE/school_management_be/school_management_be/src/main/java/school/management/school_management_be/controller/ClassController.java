package school.management.school_management_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.management.school_management_be.common.UserRole;
import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.classtype.CreateClassTypeRequest;
import school.management.school_management_be.dto.request.sclass.CreateClassRequest;
import school.management.school_management_be.dto.request.sclass.GetClassRequest;
import school.management.school_management_be.dto.request.subject.CreateSubjectRequest;
import school.management.school_management_be.dto.request.subject.GetSubjectRequest;
import school.management.school_management_be.dto.response.SuccessResponse;
import school.management.school_management_be.dto.response.classtype.CreateClassTypeResponse;
import school.management.school_management_be.dto.response.sclass.CreateClassResponse;
import school.management.school_management_be.dto.response.sclass.GetClassResponse;
import school.management.school_management_be.dto.response.subject.GetSubjectResponse;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.service.ClassService;

import java.util.Arrays;

@Controller
public class ClassController {
    @Autowired
    ClassService classService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/class/create")
    public ResponseEntity<CreateClassResponse> addClass(@RequestBody CreateClassRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        CreateClassResponse response = classService.createClass(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/class/search")
    public ResponseEntity<GetClassResponse> searchClass(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "classId", required = false) Long classId,
            @RequestParam(value = "classTypeId", required = false) Long classTypeId,
            @RequestParam(value="classCode", required = false) String classCode,
            @RequestParam(value="className", required = false) String className){
        GetClassRequest request = new GetClassRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setClassId(classId);
        request.setClassTypeId(classTypeId);
        request.setClassCode(classCode);
        request.setClassName(className);
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        GetClassResponse response = classService.searchClass(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/class/{id}")
    public ResponseEntity<SuccessResponse> editClass(@PathVariable("id") Long id, @RequestBody CreateClassRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        classService.updateClass(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/class/{id}")
    public ResponseEntity<SuccessResponse> deleteClass(@PathVariable("id") Long id, @RequestBody BaseRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        classService.deleteClass(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
