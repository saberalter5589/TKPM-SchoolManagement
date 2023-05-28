package school.management.school_management_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "s_user")
@Entity
public class SUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long uRole;

    private String userName;

    private String password;

    private String firstName;

    private String lastName;

    private String email;
}
