package school.management.school_management_be.dto.request.classtype;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class GetClassTypeRequest extends BaseRequest {
    private Long classTypeId;
    private Long classIndex;
    private String classTypeCode;
    private String classTypeName;
}
