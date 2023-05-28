package school.management.school_management_be.dto.request.classtype;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class CreateClassTypeRequest extends BaseRequest {
    Long classIndex;
    String classTypeCode;
    String classTypeName;
    String description;
    String note;
}
