package school.management.school_management_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "student")
@Entity
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private Long classId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Long gender;
    private String address;
    private String email;
    private String description;
    private String note;
}
