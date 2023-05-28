package school.management.school_management_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "score")
@Entity
public class Score extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;
    private Long studentId;
    private Long subjectId;
    private Long semester;
    private Long scoreType;
    private Double score;
}
