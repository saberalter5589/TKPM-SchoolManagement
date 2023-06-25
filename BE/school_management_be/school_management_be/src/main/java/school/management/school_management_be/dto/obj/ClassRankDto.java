package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class ClassRankDto {
    private Long classId;
    private String classCode;
    private String className;
    private Long totalStudentCount = 0L;
    private Long badCount = 0L;
    private Long avgCount = 0L;
    private Long goodCount = 0L;
    private Long veryGoodCount = 0L;
    private Long excellentCount = 0L;
}
