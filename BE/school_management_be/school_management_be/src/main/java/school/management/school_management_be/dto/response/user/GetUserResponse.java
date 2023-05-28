package school.management.school_management_be.dto.response.user;

import lombok.Data;
import school.management.school_management_be.dto.obj.UserDto;

import java.util.List;

@Data
public class GetUserResponse {
    private List<UserDto> userDtoList;
}
