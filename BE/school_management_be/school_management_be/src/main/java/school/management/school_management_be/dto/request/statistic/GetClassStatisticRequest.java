package school.management.school_management_be.dto.request.statistic;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class GetClassStatisticRequest extends BaseRequest {
    Long classId;
}
