package school.management.school_management_be.dto.request.user;

import lombok.Data;
import school.management.school_management_be.dto.request.BaseRequest;

@Data
public class GetUserRequest extends BaseRequest {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
}
