package school.management.school_management_be.mapper;

import school.management.school_management_be.dto.obj.UserDto;
import school.management.school_management_be.dto.request.user.CreateUserRequest;
import school.management.school_management_be.entity.SUser;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDxo {
    public static SUser mapFromRequestToEntity(CreateUserRequest request){
        SUser sUser = new SUser();
        sUser.setUserName(request.getUserName());
        sUser.setPassword(request.getPassword());
        sUser.setFirstName(request.getFirstName());
        sUser.setLastName(request.getLastName());
        sUser.setEmail(request.getEmail());
        return sUser;
    }

    public static List<UserDto> mapFromDbObjListToUserDtoList(List<Object[]> dbObjList){
        List<UserDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToUserDto(obj));
            }
        }
        return resultList;
    }

    private static UserDto mapFromDbObjToUserDto(Object[] obj){
        if(obj == null){
            return null;
        }
        UserDto dto = new UserDto();
        dto.setUserId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setRoleId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        dto.setUserName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setFirstName(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        dto.setLastName(obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null);
        dto.setEmail(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        return dto;
    }
}
