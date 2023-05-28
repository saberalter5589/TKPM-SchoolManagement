package school.management.school_management_be.dto.response.score;

import lombok.Data;
import school.management.school_management_be.dto.obj.ScorePartDto;

import java.util.List;

@Data
public class GetScoreResponse {
    private List<ScorePartDto> scoreParts;
}
