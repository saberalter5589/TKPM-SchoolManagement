package school.management.school_management_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.management.school_management_be.entity.ClassType;
import school.management.school_management_be.entity.SClass;
import school.management.school_management_be.entity.Subject;

public interface ClassRepository extends JpaRepository<SClass, Long>, ClassRepositoryCustom {
    @Query("SELECT sc FROM SClass sc WHERE sc.classId =:classId AND sc.isDeleted = false ")
    SClass findByClassId(Long classId);
}
