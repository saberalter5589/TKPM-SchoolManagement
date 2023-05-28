package school.management.school_management_be.dto.request.sclass;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class GetClassRequest extends BaseRequest {
    Long classId;
    Long classTypeId;
    String classCode;
    String className;
}
