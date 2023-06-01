package school.management.school_management_be.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import school.management.school_management_be.repository.ScoreRepositoryCustom;
import school.management.school_management_be.util.QueryUtil;

import java.util.HashMap;
import java.util.List;

public class ScoreRepositoryCustomImpl implements ScoreRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getStudentSubjectStatistic(Long studentId) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("WITH SUBJECT_SCORE_VIEW AS\n" +
                "\t(SELECT SV.STUDENT_ID,\n" +
                "\t\t\tSV.SUBJECT_ID,\n" +
                "\t\t\tSV.SEMESTER,\n" +
                "\t\t\tSUM(CASE SV.SCORE_TYPE\n" +
                "\t\t\t\t\t\t\t\t\t\t\tWHEN 0 THEN ROUND(SV.SCORE * SV.TEST_15_RATE,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t2)\n" +
                "\t\t\t\t\t\t\t\t\t\t\tWHEN 1 THEN ROUND(SV.SCORE * SV.TEST_45_RATE,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t2)\n" +
                "\t\t\t\t\t\t\t\t\t\t\tWHEN 2 THEN ROUND(SV.SCORE * SV.FINAL_EXAM_RATE,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t2)\n" +
                "\t\t\t\t\t\t\t\t\t\t\tELSE 0\n" +
                "\t\t\t\t\t\t\tEND) AS REFINED_SCORE,\n" +
                "\t\t\tSV.FIRST_SEM_RATE,\n" +
                "\t\t\tSV.SECOND_SEM_RATE\n" +
                "\t\tFROM SCORE_VIEW SV\n" +
                "\t\tGROUP BY SV.STUDENT_ID,\n" +
                "\t\t\tSV.SUBJECT_ID,\n" +
                "\t\t\tSV.SEMESTER,\n" +
                "\t\t\tSV.FIRST_SEM_RATE,\n" +
                "\t\t\tSV.SECOND_SEM_RATE),\n" +
                "\tSUBJECT_SCORE_AVG_VIEW AS\n" +
                "\t(SELECT SSV.STUDENT_ID,\n" +
                "\t\t\tSSV.SUBJECT_ID,\n" +
                "\t\t\tSTRING_AGG(SSV.SEMESTER || '#' || SSV.REFINED_SCORE,\n" +
                "\n" +
                "\t\t\t\t',') AS SEM_AVG_SCORE,\n" +
                "\t\t\tSUM (CASE SSV.SEMESTER\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\tWHEN 1 THEN ROUND(SSV.REFINED_SCORE * SSV.FIRST_SEM_RATE,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t2)\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\tWHEN 2 THEN ROUND(SSV.REFINED_SCORE * SSV.SECOND_SEM_RATE,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t2)\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\tELSE 0\n" +
                "\t\t\t\t\t\t\t\tEND) AS FINAL_AVG_SCORE\n" +
                "\t\tFROM SUBJECT_SCORE_VIEW SSV\n" +
                "\t\tLEFT JOIN S_RULE RL ON 1 = 1\n" +
                "\t\tGROUP BY SSV.STUDENT_ID,\n" +
                "\t\t\tSSV.SUBJECT_ID)\n" +
                "SELECT \n" +
                "SSAV.student_id,\n" +
                "SSAV.subject_id,\n" +
                "sbj.subject_code,\n" +
                "sbj.subject_name,\n" +
                "ssav.SEM_AVG_SCORE,\n" +
                "sbj.avg_score,\n" +
                "SSAV.final_avg_score,\n" +
                "CASE WHEN sbj.avg_score <= SSAV.final_avg_score THEN 0 ELSE 1 END as result\n" +
                "FROM SUBJECT_SCORE_AVG_VIEW SSAV\n" +
                "LEFT JOIN subject sbj ON SSAV.subject_id = sbj.subject_id " +
                "WHERE SSAV.student_id =:studentId " +
                "ORDER BY SSAV.subject_id ");
        params.put("studentId", studentId);
        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }

    @Override
    public List<Object[]> getClassStatistic(Long classId) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("WITH SUBJECT_SCORE_VIEW AS\n" +
                "\t(SELECT SV.STUDENT_ID,\n" +
                "\t\t\tSV.SUBJECT_ID,\n" +
                "\t\t\tSV.SEMESTER,\n" +
                "\t\t\tSUM(CASE SV.SCORE_TYPE\n" +
                "\t\t\t\t\t\t\t\t\t\t\tWHEN 0 THEN ROUND(SV.SCORE * SV.TEST_15_RATE,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t2)\n" +
                "\t\t\t\t\t\t\t\t\t\t\tWHEN 1 THEN ROUND(SV.SCORE * SV.TEST_45_RATE,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t2)\n" +
                "\t\t\t\t\t\t\t\t\t\t\tWHEN 2 THEN ROUND(SV.SCORE * SV.FINAL_EXAM_RATE,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t2)\n" +
                "\t\t\t\t\t\t\t\t\t\t\tELSE 0\n" +
                "\t\t\t\t\t\t\tEND) AS REFINED_SCORE,\n" +
                "\t\t\tSV.FIRST_SEM_RATE,\n" +
                "\t\t\tSV.SECOND_SEM_RATE\n" +
                "\t\tFROM SCORE_VIEW SV\n" +
                "\t\tGROUP BY SV.STUDENT_ID,\n" +
                "\t\t\tSV.SUBJECT_ID,\n" +
                "\t\t\tSV.SEMESTER,\n" +
                "\t\t\tSV.FIRST_SEM_RATE,\n" +
                "\t\t\tSV.SECOND_SEM_RATE),\n" +
                "\tSEMESTER_SCORE_VIEW AS\n" +
                "\t(SELECT SSV.STUDENT_ID,\n" +
                "\t\t\tSSV.SEMESTER,\n" +
                "\t\t\tROUND(AVG(SSV.REFINED_SCORE),\n" +
                "\t\t\t\t2) AS sem_avg,\n" +
                "\t\t\tSSV.FIRST_SEM_RATE,\n" +
                "\t\t\tSSV.SECOND_SEM_RATE\n" +
                "\t\tFROM SUBJECT_SCORE_VIEW SSV\n" +
                "\t\tGROUP BY SSV.STUDENT_ID,\n" +
                "\t\t\tSSV.SEMESTER,\n" +
                "\t\t\tSSV.FIRST_SEM_RATE,\n" +
                "\t\t\tSSV.SECOND_SEM_RATE\n" +
                "\t\tORDER BY SSV.STUDENT_ID,\n" +
                "\t\t\tSSV.SEMESTER)\n" +
                "SELECT \n" +
                "smsv.student_id,\n" +
                "std.first_name,\n" +
                "std.last_name,\n" +
                "STRING_AGG(smsv.semester || '#'|| smsv.sem_avg, ',') as sem_avg_score,\n" +
                "SUM(\n" +
                "\tCASE smsv.semester \n" +
                "\tWHEN 1 THEN ROUND(smsv.sem_avg * smsv.first_sem_rate,2)\n" +
                "\tWHEN 2 THEN ROUND(smsv.sem_avg * smsv.second_sem_rate,2)\n" +
                "\tELSE 0 END\n" +
                ")\n" +
                "FROM SEMESTER_SCORE_VIEW smsv\n" +
                "JOIN student std ON std.student_id = smsv.student_id AND std.is_deleted = false\n" +
                "WHERE std.CLASS_ID =:classId \n" +
                "GROUP BY smsv.student_id, std.first_name,\n" +
                "std.last_name\n" +
                "ORDER BY smsv.student_id ");

        params.put("classId", classId);
        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
