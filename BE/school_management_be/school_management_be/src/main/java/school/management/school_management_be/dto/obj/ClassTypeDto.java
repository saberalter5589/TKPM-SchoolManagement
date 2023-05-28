package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class ClassTypeDto {
    Long classTypeId;
    Long classIndex;
    String classTypeCode;
    String classTypeName;
    String description;
    String note;
}
