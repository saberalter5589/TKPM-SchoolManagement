package school.management.school_management_be.dto.response.classtype;

import lombok.Data;
import school.management.school_management_be.dto.obj.ClassTypeDto;

import java.util.List;

@Data
public class GetClassTypeResponse {
    private List<ClassTypeDto> classTypeDtoList;
}
