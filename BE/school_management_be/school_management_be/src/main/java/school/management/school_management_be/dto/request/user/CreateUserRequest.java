package school.management.school_management_be.dto.request.user;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class CreateUserRequest extends BaseRequest {
    String userName;
    String password;
    String firstName;
    String lastName;
    String email;
}
