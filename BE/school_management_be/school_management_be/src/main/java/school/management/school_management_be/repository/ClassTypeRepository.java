package school.management.school_management_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.management.school_management_be.entity.ClassType;
import school.management.school_management_be.entity.Rule;

public interface ClassTypeRepository extends JpaRepository<ClassType, Long>, ClassTypeRepositoryCustom {
    @Query("SELECT ct FROM ClassType ct WHERE ct.classTypeId =:classTypeId AND ct.isDeleted = false ")
    ClassType findByClassTypeId(Long classTypeId);
}
