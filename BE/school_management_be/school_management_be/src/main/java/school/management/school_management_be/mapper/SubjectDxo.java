package school.management.school_management_be.mapper;

import school.management.school_management_be.dto.obj.ClassTypeDto;
import school.management.school_management_be.dto.obj.SubjectDto;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

public class SubjectDxo {
    public static List<SubjectDto> mapFromDbObjListToSubjectDtoList(List<Object[]> dbObjList){
        List<SubjectDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToSubjectDto(obj));
            }
        }
        return resultList;
    }

    private static SubjectDto mapFromDbObjToSubjectDto(Object[] obj){
        if(obj == null){
            return null;
        }
        SubjectDto dto = new SubjectDto();
        dto.setSubjectId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setSubjectCode(obj[1] != null ? ObjectUtil.getValueOfString(obj[1]) : null);
        dto.setSubjectName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setAvgScore(obj[3] != null ? ObjectUtil.getValueOfDouble(obj[3]) : null);
        dto.setDescription(obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null);
        dto.setNote(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        return dto;
    }
}
