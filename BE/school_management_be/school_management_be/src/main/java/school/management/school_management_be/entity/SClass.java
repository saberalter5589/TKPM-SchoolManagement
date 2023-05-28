package school.management.school_management_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "s_class")
@Entity
public class SClass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;
    private Long classTypeId;
    private String classCode;
    private String className;
    private Long maxStudentNum;
    private String description;
    private String note;
}
