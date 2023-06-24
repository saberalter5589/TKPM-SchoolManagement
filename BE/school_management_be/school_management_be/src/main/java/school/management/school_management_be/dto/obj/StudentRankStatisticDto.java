package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class StudentRankStatisticDto {
    private Long totalStudentCount;
    private Long badCount;
    private Long avgCount;
    private Long goodCount;
    private Long veryGoodCount;
    private Long excellentCount;
}
