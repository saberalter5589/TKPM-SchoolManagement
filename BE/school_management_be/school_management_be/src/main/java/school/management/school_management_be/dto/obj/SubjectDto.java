package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class SubjectDto {
    Long subjectId;
    String subjectCode;
    String subjectName;
    Double avgScore;
    String description;
    String note;
}
