package school.management.school_management_be.mapper;

import school.management.school_management_be.dto.obj.ClassTypeDto;
import school.management.school_management_be.dto.obj.StudentSemesterScoreStatisticDto;
import school.management.school_management_be.dto.obj.StudentSubjectScoreStatisticDto;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

public class StatisticDxo {
    public static List<StudentSubjectScoreStatisticDto> mapFromDbObjListToStudentSubjectDtoList(List<Object[]> dbObjList){
        List<StudentSubjectScoreStatisticDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToStudentSubjectDto(obj));
            }
        }
        return resultList;
    }

    private static StudentSubjectScoreStatisticDto mapFromDbObjToStudentSubjectDto(Object[] obj){
        if(obj == null){
            return null;
        }
        StudentSubjectScoreStatisticDto dto = new StudentSubjectScoreStatisticDto();
        dto.setStudentId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setSubjectId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        dto.setSubjectCode(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setSubjectName(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);

        String semAvgScoreStr = obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null;
        if(!CommonUtil.isNullOrWhitespace(semAvgScoreStr)){
            String[] semAvgScoreInfo = semAvgScoreStr.split(",");
            for(String avgInfo : semAvgScoreInfo){
                String[] infos = avgInfo.split("#");
                String sem = infos[0];
                String score = infos[1];

                switch (sem) {
                    case "1" -> dto.setFirstSemSubAvgScore(score != null ? ObjectUtil.getValueOfDouble(score) : null);
                    case "2" -> dto.setSecondSemSubAvgScore(score != null ? ObjectUtil.getValueOfDouble(score) : null);
                }
            }
        }

        dto.setSubjectAvgScore(obj[5] != null ? ObjectUtil.getValueOfDouble(obj[5]) : null);
        dto.setFinalSubAvgScore(obj[6] != null ? ObjectUtil.getValueOfDouble(obj[6]) : null);
        dto.setResult(obj[7] != null ? ObjectUtil.getValueOfLong(obj[7]) : null);

        return dto;
    }

    public static List<StudentSemesterScoreStatisticDto> mapFromDbObjListToStudentSemesterDtoList(List<Object[]> dbObjList){
        List<StudentSemesterScoreStatisticDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToStudentSemesterDto(obj));
            }
        }
        return resultList;
    }

    private static StudentSemesterScoreStatisticDto mapFromDbObjToStudentSemesterDto(Object[] obj){
        if(obj == null){
            return null;
        }
        StudentSemesterScoreStatisticDto dto = new StudentSemesterScoreStatisticDto();
        dto.setStudentId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setFirstName(obj[1] != null ? ObjectUtil.getValueOfString(obj[1]) : null);
        dto.setLastName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);

        String semAvgScoreStr = obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null;
        if(!CommonUtil.isNullOrWhitespace(semAvgScoreStr)){
            String[] semAvgScoreInfo = semAvgScoreStr.split(",");
            for(String avgInfo : semAvgScoreInfo){
                String[] infos = avgInfo.split("#");
                String sem = infos[0];
                String score = infos[1];

                switch (sem) {
                    case "1" -> dto.setFirstSemAvgScore(score != null ? ObjectUtil.getValueOfDouble(score) : null);
                    case "2" -> dto.setSecondSemAvgScore(score != null ? ObjectUtil.getValueOfDouble(score) : null);
                }
            }
        }
        dto.setFinalSemAvgScore(obj[4] != null ? ObjectUtil.getValueOfDouble(obj[4]) : null);
        return dto;
    }
}
