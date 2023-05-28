package school.management.school_management_be.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import school.management.school_management_be.dto.obj.MessageInfo;

@Data
public class NoDataFoundException extends BaseException {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    public NoDataFoundException(MessageInfo messageInfo){
        super(messageInfo.getMessage());
        this.messageInfo = messageInfo;
    }
}
