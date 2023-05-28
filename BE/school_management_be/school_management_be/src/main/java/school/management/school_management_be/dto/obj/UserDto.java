package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class UserDto {
    Long userId;
    Long roleId;
    String userName;
    String password;
    String firstName;
    String lastName;
    String email;
}
