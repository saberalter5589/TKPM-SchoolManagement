package school.management.school_management_be.dto.request;

import lombok.Data;
import school.management.school_management_be.dto.obj.AuthDto;

@Data
public class BaseRequest {
    private AuthDto authentication = new AuthDto();
}
