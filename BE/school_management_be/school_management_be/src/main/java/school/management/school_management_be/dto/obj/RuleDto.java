package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class RuleDto {
    Long userId;
    Long minAge;
    Long maxAge;
    Long test15Rate;
    Long test45Rate;
    Long finalExamRate;
    Long firstSemRate;
    Long secondSemRate;
    Double badAverage;
    Double avgAverage;
    Double goodAverage;
    Double veryGoodAverage;
    Double excellentAverage;
}
