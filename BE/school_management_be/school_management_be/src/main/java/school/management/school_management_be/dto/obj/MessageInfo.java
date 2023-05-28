package school.management.school_management_be.dto.obj;

import lombok.Data;

@Data
public class MessageInfo {
    String code;
    String message;

    public MessageInfo(String code, String message){
        this.code = code;
        this.message = message;
    }
}
