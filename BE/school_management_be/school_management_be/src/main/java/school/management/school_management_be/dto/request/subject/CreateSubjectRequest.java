package school.management.school_management_be.dto.request.subject;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class CreateSubjectRequest extends BaseRequest {
    private String subjectCode;
    private String subjectName;
    private Double avgScore;
    private String description;
    private String note;
}
