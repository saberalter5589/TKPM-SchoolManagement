package school.management.school_management_be.exception;

import lombok.Data;
import school.management.school_management_be.dto.obj.MessageInfo;

@Data
public class BaseException extends RuntimeException {
    MessageInfo messageInfo;

    public BaseException(){

    }

    public BaseException(String message){
        super(message);
    }
}
