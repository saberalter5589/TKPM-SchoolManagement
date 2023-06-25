package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.statistic.GetClassRankStatisticRequest;
import school.management.school_management_be.dto.request.statistic.GetClassStatisticRequest;
import school.management.school_management_be.dto.request.statistic.GetStudentStatisticRequest;
import school.management.school_management_be.dto.response.statistic.GetClassRankStatisticResponse;
import school.management.school_management_be.dto.response.statistic.GetClassStatisticResponse;
import school.management.school_management_be.dto.response.statistic.GetStudentStatisticResponse;

public interface StatisticService {
    GetStudentStatisticResponse getStudentSubjectStatistic(GetStudentStatisticRequest request);
    GetClassStatisticResponse getClassStatistic(GetClassStatisticRequest request);
    GetClassRankStatisticResponse getClassRankStatistic(GetClassRankStatisticRequest request);
}
