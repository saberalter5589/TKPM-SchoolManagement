package school.management.school_management_be.dto.request.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
