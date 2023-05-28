package school.management.school_management_be.dto.request.sclass;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class CreateClassRequest extends BaseRequest {
    Long classTypeId;
    String classCode;
    String className;
    Long maxStudentNum;
    String description;
    String note;
}
