package school.management.school_management_be.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import school.management.school_management_be.dto.obj.MessageInfo;

@Data
public class UnAuthorizationException extends BaseException {
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public UnAuthorizationException(MessageInfo messageInfo){
        super(messageInfo.getMessage());
        this.messageInfo = messageInfo;
    }
}
