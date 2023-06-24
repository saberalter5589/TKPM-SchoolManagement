package school.management.school_management_be.dto.request.rule;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class UpdateRuleRequest extends BaseRequest {
    private Long minAge;
    private Long maxAge;
    private Long test15Rate;
    private Long test45Rate;
    private Long finalExamRate;
    private Long firstSemRate;
    private Long secondSemRate;
    private Double badAverage;
    private Double avgAverage;
    private Double goodAverage;
    private Double veryGoodAverage;
    private Double excellentAverage;
}
