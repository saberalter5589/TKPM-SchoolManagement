package school.management.school_management_be.repository;

import school.management.school_management_be.entity.Rule;

import java.util.List;

public interface ScoreRepositoryCustom {
    List<Object[]> getStudentSubjectStatistic(Long studentId);
    List<Object[]> getClassStatistic(Long classId, Rule rule);
    List<Object[]> getClassRankStatistic(Long classId, Rule rule);
    List<Object[]> getAllClassRankStatistic(Rule rule);
}
