package school.management.school_management_be.dto.response.classtype;

import lombok.Data;
import school.management.school_management_be.dto.obj.ClassTypeDto;
import school.management.school_management_be.dto.response.SuccessResponse;

import java.util.List;

@Data
public class CreateClassTypeResponse extends SuccessResponse {
    private ClassTypeDto classTypeDto;
}
