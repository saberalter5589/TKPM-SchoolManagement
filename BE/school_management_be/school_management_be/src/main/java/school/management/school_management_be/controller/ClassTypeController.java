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
import school.management.school_management_be.dto.request.user.CreateUserRequest;
import school.management.school_management_be.dto.request.user.DeleteUserRequest;
import school.management.school_management_be.dto.response.SuccessResponse;
import school.management.school_management_be.dto.response.classtype.CreateClassTypeResponse;
import school.management.school_management_be.dto.response.classtype.GetClassTypeResponse;
import school.management.school_management_be.dto.response.user.CreateUserResponse;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.service.ClassTypeService;

import java.util.Arrays;

@Controller
public class ClassTypeController {
    @Autowired
    ClassTypeService classTypeService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/class-type/create")
    public ResponseEntity<CreateClassTypeResponse> addClassType(@RequestBody CreateClassTypeRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        CreateClassTypeResponse response = classTypeService.createClassType(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/class-type/search")
    public ResponseEntity<GetClassTypeResponse> searchClassType(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "classTypeId", required = false) Long classTypeId,
            @RequestParam(value="classTypeCode", required = false) String classTypeCode,
            @RequestParam(value="classTypeName", required = false) String classTypeName){
        GetClassTypeRequest request = new GetClassTypeRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setClassTypeId(classTypeId);
        request.setClassTypeCode(classTypeCode);
        request.setClassTypeName(classTypeName);
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        GetClassTypeResponse response = classTypeService.searchClassType(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/class-type/{id}")
    public ResponseEntity<SuccessResponse> editClassType(@PathVariable("id") Long id, @RequestBody CreateClassTypeRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        classTypeService.updateClassType(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/class-type/{id}")
    public ResponseEntity<SuccessResponse> deleteUser(@PathVariable("id") Long id, @RequestBody BaseRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        classTypeService.deleteClassType(id);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
