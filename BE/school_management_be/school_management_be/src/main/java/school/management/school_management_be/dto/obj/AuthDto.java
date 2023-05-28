package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class AuthDto {
    Long userId;
    String password;
}
