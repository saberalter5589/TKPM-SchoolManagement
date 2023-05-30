package school.management.school_management_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.management.school_management_be.common.UserRole;
import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.user.CreateUserRequest;
import school.management.school_management_be.dto.request.user.DeleteUserRequest;
import school.management.school_management_be.dto.request.user.GetUserRequest;
import school.management.school_management_be.dto.response.SuccessResponse;
import school.management.school_management_be.dto.response.user.CreateUserResponse;
import school.management.school_management_be.dto.response.user.GetUserResponse;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.service.SUserService;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    SUserService sUserService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/staff/create")
    public ResponseEntity<CreateUserResponse> addUser(@RequestBody CreateUserRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        CreateUserResponse response = sUserService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/staff/search")
    public ResponseEntity<GetUserResponse> searchUser(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "staffId", required = false) String staffId,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email){
        GetUserRequest request = new GetUserRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setUserId(staffId == null ? null : Long.valueOf(staffId));
        request.setUserName(userName);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);

        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        GetUserResponse response = sUserService.searchUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/staff/{id}")
    public ResponseEntity<SuccessResponse> editUser(@PathVariable("id") Long id, @RequestBody CreateUserRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        sUserService.updateUser(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<SuccessResponse> deleteUser(@PathVariable("id") Long id,
                                                      @RequestHeader("userId") Long userId,
                                                      @RequestHeader("password") String password){
        DeleteUserRequest request = new DeleteUserRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        sUserService.deleteUser(id);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
