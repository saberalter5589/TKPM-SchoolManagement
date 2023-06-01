package school.management.school_management_be.dto.response.statistic;

import lombok.Data;
import school.management.school_management_be.dto.obj.StudentSubjectScoreStatisticDto;

import java.util.List;

@Data
public class GetStudentStatisticResponse {
    private String studentName;
    private String className;
    private List<StudentSubjectScoreStatisticDto> subjectDtoList;
}
