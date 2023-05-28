package school.management.school_management_be.util;

import school.management.school_management_be.common.enums.MessageEnum;
import school.management.school_management_be.dto.obj.MessageInfo;

import java.text.MessageFormat;

public class MessageUtil {
    public static MessageInfo formatMessage(Integer code, String... params){
        MessageEnum messageEnum = MessageEnum.getByCode(code);
        String customMessage = messageEnum.getMessage();
        if(params.length > 0){
            customMessage = MessageFormat.format(customMessage, (Object[]) params);
        }
        return new MessageInfo(messageEnum.getCode().toString(), customMessage);
    }
}
