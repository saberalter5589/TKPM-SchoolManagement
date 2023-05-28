package school.management.school_management_be.dto.request.student;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class GetStudentRequest extends BaseRequest {
    Long studentId;
    Long classId;
    String firstName;
    String lastName;
    Long gender;
    String address;
    String email;
}
