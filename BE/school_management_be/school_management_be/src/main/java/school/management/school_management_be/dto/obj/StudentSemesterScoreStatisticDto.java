package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class StudentSemesterScoreStatisticDto {
    Long studentId;
    String firstName;
    String lastName;
    Double firstSemAvgScore;
    Double secondSemAvgScore;
    Double finalSemAvgScore;
    Long rank;
}
