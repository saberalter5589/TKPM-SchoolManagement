package school.management.school_management_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.management.school_management_be.entity.ClassType;
import school.management.school_management_be.entity.SUser;
import school.management.school_management_be.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {
    @Query("SELECT sbj FROM Subject sbj WHERE sbj.subjectId =:subjectId AND sbj.isDeleted = false ")
    Subject findBySubjectId(Long subjectId);
}
