package school.management.school_management_be.dto.response.student;

import lombok.Data;
import school.management.school_management_be.dto.obj.StudentDto;

import java.util.List;

@Data
public class GetStudentResponse {
    List<StudentDto> studentDtoList;
}
