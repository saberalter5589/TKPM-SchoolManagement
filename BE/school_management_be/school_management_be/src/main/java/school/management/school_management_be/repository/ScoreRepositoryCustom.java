package school.management.school_management_be.repository;

import java.util.List;

public interface ScoreRepositoryCustom {
    List<Object[]> getStudentSubjectStatistic(Long studentId);
    List<Object[]> getClassStatistic(Long classId);
}
