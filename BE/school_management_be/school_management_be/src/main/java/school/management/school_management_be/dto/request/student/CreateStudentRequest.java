package school.management.school_management_be.dto.request.student;

import lombok.Data;
import school.management.school_management_be.dto.obj.ClassDto;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class CreateStudentRequest extends BaseRequest {
    Long classId;
    String firstName;
    String lastName;
    String birthDate;
    Long gender;
    String address;
    String email;
    String description;
    String note;
}
