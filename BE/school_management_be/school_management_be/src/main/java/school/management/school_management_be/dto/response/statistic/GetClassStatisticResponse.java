package school.management.school_management_be.dto.response.statistic;

import lombok.Data;
import school.management.school_management_be.dto.obj.StudentSemesterScoreStatisticDto;

import java.util.List;

@Data
public class GetClassStatisticResponse {
    private List<StudentSemesterScoreStatisticDto> semesterDtoList;
}
