package school.management.school_management_be.mapper;

import school.management.school_management_be.common.DateTimeFormat;
import school.management.school_management_be.dto.obj.ClassDto;
import school.management.school_management_be.dto.obj.StudentDto;
import school.management.school_management_be.dto.obj.SubjectDto;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.DateTimeUtil;
import school.management.school_management_be.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDxo {
    public static List<StudentDto> mapFromDbObjListToDtoList(List<Object[]> dbObjList){
        List<StudentDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToDto(obj));
            }
        }
        return resultList;
    }

    private static StudentDto mapFromDbObjToDto(Object[] obj){
        if(obj == null){
            return null;
        }
        StudentDto dto = new StudentDto();
        dto.setStudentId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);

        ClassDto classDto = new ClassDto();
        classDto.setClassId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        classDto.setClassCode(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        classDto.setClassName(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        classDto.setMaxStudentNum(obj[4] != null ? ObjectUtil.getValueOfLong(obj[4]) : null);

        dto.setFirstName(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        dto.setLastName(obj[6] != null ? ObjectUtil.getValueOfString(obj[6]) : null);
        dto.setBirthDate(obj[7] != null ? DateTimeUtil.convertFromDateToString((Date)obj[7], DateTimeFormat.DATE): null);
        dto.setGender(obj[8] != null ? ObjectUtil.getValueOfLong(obj[8]) : null);
        dto.setAddress(obj[9] != null ? ObjectUtil.getValueOfString(obj[9]) : null);
        dto.setEmail(obj[10] != null ? ObjectUtil.getValueOfString(obj[10]) : null);
        dto.setDescription(obj[11] != null ? ObjectUtil.getValueOfString(obj[11]) : null);
        dto.setNote(obj[12] != null ? ObjectUtil.getValueOfString(obj[12]) : null);
        return dto;
    }
}
