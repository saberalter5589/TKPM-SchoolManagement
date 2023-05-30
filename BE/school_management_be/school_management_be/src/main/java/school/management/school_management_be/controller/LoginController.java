package school.management.school_management_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.dto.request.user.*;
import school.management.school_management_be.dto.response.user.*;

@CrossOrigin(origins = "*")
@Controller
public class LoginController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = authenticationService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
