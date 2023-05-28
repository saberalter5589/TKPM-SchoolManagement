package school.management.school_management_be.dto.response.user;

import lombok.Data;
import school.management.school_management_be.dto.obj.UserDto;
import school.management.school_management_be.dto.response.SuccessResponse;

@Data
public class CreateUserResponse extends SuccessResponse {
    UserDto user;
}
