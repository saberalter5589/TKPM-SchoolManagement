package school.management.school_management_be.dto.response.sclass;

import lombok.Data;
import school.management.school_management_be.dto.obj.ClassDto;

import java.util.List;

@Data
public class GetClassResponse {
    List<ClassDto> classDtoList;
}
