package school.management.school_management_be.mapper;

import school.management.school_management_be.dto.obj.ClassDto;
import school.management.school_management_be.dto.obj.ClassTypeDto;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

public class ClassDxo {
    public static List<ClassDto> mapFromDbObjListToClassDtoList(List<Object[]> dbObjList){
        List<ClassDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToClassDto(obj));
            }
        }
        return resultList;
    }

    private static ClassDto mapFromDbObjToClassDto(Object[] obj){
        if(obj == null){
            return null;
        }
        ClassDto dto = new ClassDto();
        dto.setClassId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);

        ClassTypeDto classTypeDto = new ClassTypeDto();
        classTypeDto.setClassTypeId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        classTypeDto.setClassIndex(obj[2] != null ? ObjectUtil.getValueOfLong(obj[2]) : null);
        classTypeDto.setClassTypeCode(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        classTypeDto.setClassTypeName(obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null);
        dto.setClassTypeDto(classTypeDto);

        dto.setClassCode(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        dto.setClassName(obj[6] != null ? ObjectUtil.getValueOfString(obj[6]) : null);
        dto.setMaxStudentNum(obj[7] != null ? ObjectUtil.getValueOfLong(obj[7]) : null);
        dto.setDescription(obj[8] != null ? ObjectUtil.getValueOfString(obj[8]) : null);
        dto.setNote(obj[9] != null ? ObjectUtil.getValueOfString(obj[9]) : null);
        dto.setStudentCount(obj[10] != null ? ObjectUtil.getValueOfLong(obj[10]) : 0);
        return dto;
    }
}
