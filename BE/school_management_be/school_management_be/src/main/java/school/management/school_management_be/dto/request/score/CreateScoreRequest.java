package school.management.school_management_be.dto.request.score;

import lombok.Data;
import school.management.school_management_be.dto.obj.ScorePartDto;
import school.management.school_management_be.dto.request.BaseRequest;

import java.util.List;

@Data
public class CreateScoreRequest extends BaseRequest {
    private Long studentId;
    private Long subjectId;
    private Long semester;
    private List<ScorePartDto> scoreParts;
}
