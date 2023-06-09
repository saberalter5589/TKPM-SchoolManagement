package school.management.school_management_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "s_rule")
@Entity
public class Rule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;

    private Long minAge;

    private Long maxAge;
    @Column(name="test_15_rate")
    private Long test15Rate;
    @Column(name="test_45_rate")
    private Long test45Rate;
    private Long finalExamRate;
    private Long firstSemRate;
    private Long secondSemRate;
    @Column(name="bad_avg")
    private Double badAverage;
    @Column(name="avg_avg")
    private Double avgAverage;
    @Column(name="good_avg")
    private Double goodAverage;
    @Column(name="very_good_avg")
    private Double veryGoodAverage;
    @Column(name="excellent_avg")
    private Double excellentAverage;
}
