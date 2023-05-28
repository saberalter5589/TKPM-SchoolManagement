package school.management.school_management_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "class_type")
@Entity
public class ClassType extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classTypeId;
    private Long classIndex;
    private String classTypeCode;
    private String classTypeName;
    private String description;
    private String note;
}
