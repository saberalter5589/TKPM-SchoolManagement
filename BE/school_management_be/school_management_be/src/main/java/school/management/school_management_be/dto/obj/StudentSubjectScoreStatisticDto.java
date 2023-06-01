package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class StudentSubjectScoreStatisticDto {
    Long studentId;
    Long subjectId;
    String subjectCode;
    String subjectName;
    Double firstSemSubAvgScore;
    Double secondSemSubAvgScore;
    Double finalSubAvgScore;
    Double subjectAvgScore;
    Long result;
}
