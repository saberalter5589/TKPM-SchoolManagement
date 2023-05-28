package school.management.school_management_be.dto.request.score;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class GetScoreRequest extends BaseRequest {
    private Long studentId;
    private Long subjectId;
    private Long semester;
}
