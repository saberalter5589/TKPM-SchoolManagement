package school.management.school_management_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.management.school_management_be.common.UserRole;
import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.student.CreateStudentRequest;
import school.management.school_management_be.dto.request.student.GetStudentRequest;
import school.management.school_management_be.dto.request.subject.CreateSubjectRequest;
import school.management.school_management_be.dto.request.subject.GetSubjectRequest;
import school.management.school_management_be.dto.response.SuccessResponse;
import school.management.school_management_be.dto.response.student.CreateStudentResponse;
import school.management.school_management_be.dto.response.student.GetStudentResponse;
import school.management.school_management_be.dto.response.subject.CreateSubjectResponse;
import school.management.school_management_be.dto.response.subject.GetSubjectResponse;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.service.StudentService;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/student/create")
    public ResponseEntity<CreateStudentResponse> addStudent(@RequestBody CreateStudentRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        CreateStudentResponse response = studentService.createStudent(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/student/search")
    public ResponseEntity<GetStudentResponse> searchStudent(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value="classId", required = false) Long classId,
            @RequestParam(value="firstName", required = false) String firstName,
            @RequestParam(value="lastName", required = false) String lastName,
            @RequestParam(value="gender", required = false) Long gender,
            @RequestParam(value="address", required = false) String address,
            @RequestParam(value="email", required = false) String email){
        GetStudentRequest request = new GetStudentRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);

        request.setStudentId(studentId);
        request.setClassId(classId);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setGender(gender);
        request.setAddress(address);
        request.setEmail(email);

        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        GetStudentResponse response = studentService.searchStudent(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<SuccessResponse> editStudent(@PathVariable("id") Long id, @RequestBody CreateStudentRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        studentService.updateStudent(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<SuccessResponse> deleteStudent(@PathVariable("id") Long id, @RequestBody BaseRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserRole.ADMIN, UserRole.STAFF));
        studentService.deleteStudent(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
