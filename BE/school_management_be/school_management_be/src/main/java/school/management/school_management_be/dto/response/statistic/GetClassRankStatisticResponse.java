package school.management.school_management_be.dto.response.statistic;

import lombok.Data;
import school.management.school_management_be.dto.obj.ClassRankDto;

import java.util.List;

@Data
public class GetClassRankStatisticResponse {
    private List<ClassRankDto> classRankDtoList;
}
