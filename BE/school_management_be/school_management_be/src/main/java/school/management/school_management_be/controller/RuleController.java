package school.management.school_management_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.management.school_management_be.common.UserRole;
import school.management.school_management_be.dto.request.rule.GetRuleRequest;
import school.management.school_management_be.dto.request.rule.UpdateRuleRequest;
import school.management.school_management_be.dto.request.user.CreateUserRequest;
import school.management.school_management_be.dto.request.user.GetUserRequest;
import school.management.school_management_be.dto.response.SuccessResponse;
import school.management.school_management_be.dto.response.rule.GetRuleResponse;
import school.management.school_management_be.dto.response.user.GetUserResponse;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.service.RuleService;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class RuleController {
    @Autowired
    RuleService ruleService;
    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/rule/search")
    public ResponseEntity<GetRuleResponse> searchRule(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password){
        GetRuleRequest request = new GetRuleRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);

        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        GetRuleResponse response = ruleService.getRule(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/rule")
    public ResponseEntity<SuccessResponse> editRule(@RequestBody UpdateRuleRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN));
        ruleService.updateRule(request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
