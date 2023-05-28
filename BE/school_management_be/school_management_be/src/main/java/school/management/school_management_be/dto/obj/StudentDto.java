package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class StudentDto {
    Long studentId;
    ClassDto classDto;
    String firstName;
    String lastName;
    String birthDate;
    Long gender;
    String address;
    String email;
    String description;
    String note;
}
