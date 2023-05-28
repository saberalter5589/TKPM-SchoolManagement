package school.management.school_management_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name="is_deleted")
    private Boolean isDeleted;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="created_user_id")
    private Long createdUserId;

    @Column(name="updated_user_id")
    private Long updatedUserId;
}
