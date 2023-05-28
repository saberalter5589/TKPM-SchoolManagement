package school.management.school_management_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.management.school_management_be.entity.Score;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long>, ScoreRepositoryCustom {
    @Query("SELECT scr FROM Score scr WHERE scr.studentId =:studentId " +
            "AND scr.subjectId =:subjectId " +
            "AND scr.semester =:semester " +
            "AND scr.isDeleted = false ")
    List<Score> findStudentScore(Long studentId, Long subjectId, Long semester);
}
