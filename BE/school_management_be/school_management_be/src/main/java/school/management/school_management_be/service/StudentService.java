package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.student.CreateStudentRequest;
import school.management.school_management_be.dto.request.student.GetStudentRequest;
import school.management.school_management_be.dto.response.student.CreateStudentResponse;
import school.management.school_management_be.dto.response.student.GetStudentResponse;

public interface StudentService {
    CreateStudentResponse createStudent(CreateStudentRequest request);
    GetStudentResponse searchStudent(GetStudentRequest request);
    void updateStudent(Long id, CreateStudentRequest request);
    void deleteStudent(Long id, BaseRequest request);
}
