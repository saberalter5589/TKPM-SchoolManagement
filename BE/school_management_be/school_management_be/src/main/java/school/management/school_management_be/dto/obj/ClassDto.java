package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class ClassDto {
    Long classId;
    ClassTypeDto classTypeDto;
    String classCode;
    String className;
    Long maxStudentNum;
    String description;
    String note;
}
