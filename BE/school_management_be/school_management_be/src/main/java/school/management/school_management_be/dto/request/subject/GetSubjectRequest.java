package school.management.school_management_be.dto.request.subject;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class GetSubjectRequest extends BaseRequest {
    private Long subjectId;
    private String subjectCode;
    private String subjectName;
}
