package school.management.school_management_be.dto.response.user;

import lombok.Data;
import school.management.school_management_be.dto.obj.UserDto;

@Data
public class LoginResponse {
    private UserDto user;
}
