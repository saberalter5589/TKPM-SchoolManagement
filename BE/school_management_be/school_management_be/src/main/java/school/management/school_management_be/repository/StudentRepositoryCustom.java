package school.management.school_management_be.repository;

import school.management.school_management_be.dto.request.student.GetStudentRequest;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Object[]> searchStudent(GetStudentRequest request);
}
