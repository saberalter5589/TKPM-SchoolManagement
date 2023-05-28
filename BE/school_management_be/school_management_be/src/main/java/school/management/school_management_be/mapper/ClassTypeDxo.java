package school.management.school_management_be.mapper;

import school.management.school_management_be.dto.obj.ClassTypeDto;
import school.management.school_management_be.dto.obj.UserDto;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

public class ClassTypeDxo {

    public static List<ClassTypeDto> mapFromDbObjListToClassTypeDtoList(List<Object[]> dbObjList){
        List<ClassTypeDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToClassTypeDto(obj));
            }
        }
        return resultList;
    }

    private static ClassTypeDto mapFromDbObjToClassTypeDto(Object[] obj){
        if(obj == null){
            return null;
        }
        ClassTypeDto dto = new ClassTypeDto();
        dto.setClassTypeId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setClassIndex(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        dto.setClassTypeCode(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setClassTypeName(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        dto.setDescription(obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null);
        dto.setNote(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        return dto;
    }
}
