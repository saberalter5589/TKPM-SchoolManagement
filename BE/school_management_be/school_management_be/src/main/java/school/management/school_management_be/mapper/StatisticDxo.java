package school.management.school_management_be.mapper;

import school.management.school_management_be.dto.obj.*;
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
        dto.setRank(obj[5] != null ? ObjectUtil.getValueOfLong(obj[5]) : null);
        return dto;
    }

    public static StudentRankStatisticDto convertToDto(Long studentCount, List<Object[]> rankCountObjList){
        StudentRankStatisticDto dto = new StudentRankStatisticDto();
        dto.setTotalStudentCount(studentCount);

        if(!CommonUtil.isNullOrEmpty(rankCountObjList)){
            for(Object[] obj : rankCountObjList){
                Long rankType = obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null;
                if(rankType == null){
                    continue;
                }
                if (rankType == 0L) {
                    dto.setBadCount(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
                } else if (rankType == 1L) {
                    dto.setAvgCount(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
                } else if (rankType == 2L) {
                    dto.setGoodCount(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
                } else if (rankType == 3L) {
                    dto.setVeryGoodCount(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
                } else if (rankType == 4L) {
                    dto.setExcellentCount(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
                }
            }
        }
        return dto;
    }

    public static List<ClassRankDto> mapFromDbObjToClassRankDtoList(List<Object[]> dbObjList){
        List<ClassRankDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                ClassRankDto rankDto = new ClassRankDto();

                rankDto.setClassId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
                rankDto.setClassCode(obj[1] != null ? ObjectUtil.getValueOfString(obj[1]) : null);
                rankDto.setClassName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
                rankDto.setTotalStudentCount(obj[3] != null ? ObjectUtil.getValueOfLong(obj[3]) : 0);
                if(!CommonUtil.isNullOrWhitespace(ObjectUtil.getValueOfString(obj[4]))){
                    String[] rankInfos = ObjectUtil.getValueOfString(obj[4]).split(",");
                    for(String avgInfo : rankInfos){
                        String[] infos = avgInfo.split("#");
                        Long rank = ObjectUtil.getValueOfLong(infos[0]) ;
                        Long count = ObjectUtil.getValueOfLong(infos[1]) ;
                        if(rank == 0L){
                            rankDto.setBadCount(count);
                        } else if (rank == 1L) {
                            rankDto.setAvgCount(count);
                        } else if (rank == 2L) {
                            rankDto.setGoodCount(count);
                        } else if (rank == 3L) {
                            rankDto.setVeryGoodCount(count);
                        }else if (rank == 4L) {
                            rankDto.setExcellentCount(count);
                        }
                    }
                }
                resultList.add(rankDto);
            }
        }
        return resultList;
    }
}
