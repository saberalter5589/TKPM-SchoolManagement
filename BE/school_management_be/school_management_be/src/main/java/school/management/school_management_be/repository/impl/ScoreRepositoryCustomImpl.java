package school.management.school_management_be.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import school.management.school_management_be.entity.Rule;
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
        sql.append("WITH SUBJECT_SCORE_VIEW AS (" +
                "SELECT SV.STUDENT_ID,SV.SUBJECT_ID,SV.SEMESTER, " +
                "SUM(CASE SV.SCORE_TYPE " +
                "WHEN 0 THEN ROUND(SV.SCORE * SV.TEST_15_RATE, 2) " +
                "WHEN 1 THEN ROUND(SV.SCORE * SV.TEST_45_RATE, 2) " +
                "WHEN 2 THEN ROUND(SV.SCORE * SV.FINAL_EXAM_RATE, 2) ELSE 0 END) AS REFINED_SCORE, " +
                "SV.FIRST_SEM_RATE," +
                "SV.SECOND_SEM_RATE " +
                "FROM SCORE_VIEW SV " +
                "GROUP BY SV.STUDENT_ID, " +
                "SV.SUBJECT_ID, " +
                "SV.SEMESTER, " +
                "SV.FIRST_SEM_RATE, " +
                "SV.SECOND_SEM_RATE), " +
                "SUBJECT_SCORE_AVG_VIEW AS " +
                "(SELECT SSV.STUDENT_ID," +
                "SSV.SUBJECT_ID, " +
                "STRING_AGG(SSV.SEMESTER || '#' || SSV.REFINED_SCORE,',') AS SEM_AVG_SCORE, " +
                "SUM (CASE SSV.SEMESTER " +
                "WHEN 1 THEN ROUND(SSV.REFINED_SCORE * SSV.FIRST_SEM_RATE,2) " +
                "WHEN 2 THEN ROUND(SSV.REFINED_SCORE * SSV.SECOND_SEM_RATE,2) ELSE 0 " +
                "END) AS FINAL_AVG_SCORE " +
                "FROM SUBJECT_SCORE_VIEW SSV " +
                "LEFT JOIN S_RULE RL ON 1 = 1 " +
                "GROUP BY SSV.STUDENT_ID, " +
                "SSV.SUBJECT_ID) " +
                "SELECT " +
                "SSAV.student_id, " +
                "SSAV.subject_id, " +
                "sbj.subject_code, " +
                "sbj.subject_name, " +
                "ssav.SEM_AVG_SCORE," +
                "sbj.avg_score," +
                "SSAV.final_avg_score," +
                "CASE WHEN sbj.avg_score <= SSAV.final_avg_score THEN 0 ELSE 1 END as result " +
                "FROM SUBJECT_SCORE_AVG_VIEW SSAV " +
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
    public List<Object[]> getClassStatistic(Long classId, Rule rule) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        buildGetClassStatisticWithQuery(sql);
        sql.append("SELECT * FROM CLASS_FIN_SCORE_VIEW ");

        params.put("classId", classId);
        params.put("badAvg", rule.getBadAverage());
        params.put("avgAvg", rule.getAvgAverage());
        params.put("goodAvg", rule.getGoodAverage());
        params.put("veryGoodAvg", rule.getVeryGoodAverage());

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }

    @Override
    public List<Object[]> getClassRankStatistic(Long classId, Rule rule) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        buildGetClassStatisticWithQuery(sql);
        sql.append("SELECT cfsv.s_rank, COUNT(cfsv.student_id) " +
                "FROM CLASS_FIN_SCORE_VIEW cfsv " +
                "GROUP BY cfsv.s_rank ");

        params.put("classId", classId);
        params.put("badAvg", rule.getBadAverage());
        params.put("avgAvg", rule.getAvgAverage());
        params.put("goodAvg", rule.getGoodAverage());
        params.put("veryGoodAvg", rule.getVeryGoodAverage());

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }

    private void buildGetClassStatisticWithQuery(StringBuilder sql){
        sql.append("WITH SUBJECT_SCORE_VIEW AS (");
        sql.append("SELECT SV.STUDENT_ID, SV.SUBJECT_ID, SV.SEMESTER, ");
        sql.append("SUM (CASE SV.SCORE_TYPE ");
        sql.append("WHEN 0 THEN ROUND(SV.SCORE * SV.TEST_15_RATE, 2) ");
        sql.append("WHEN 1 THEN ROUND(SV.SCORE * SV.TEST_45_RATE, 2) ");
        sql.append("WHEN 2 THEN ROUND(SV.SCORE * SV.FINAL_EXAM_RATE, 2) ");
        sql.append("ELSE 0 END ) AS REFINED_SCORE, ");
        sql.append("SV.FIRST_SEM_RATE, SV.SECOND_SEM_RATE ");
        sql.append("FROM SCORE_VIEW SV ");
        sql.append("GROUP BY SV.STUDENT_ID,SV.SUBJECT_ID,SV.SEMESTER,SV.FIRST_SEM_RATE, SV.SECOND_SEM_RATE ");
        sql.append("),");

        sql.append("SEMESTER_SCORE_VIEW AS (");
        sql.append("SELECT SSV.STUDENT_ID, SSV.SEMESTER," +
                "ROUND(AVG(SSV.REFINED_SCORE),2) AS SEM_AVG," +
                "SSV.FIRST_SEM_RATE," +
                "SSV.SECOND_SEM_RATE " +
                "FROM SUBJECT_SCORE_VIEW SSV " +
                "GROUP BY SSV.STUDENT_ID,SSV.SEMESTER,SSV.FIRST_SEM_RATE,SSV.SECOND_SEM_RATE " +
                "ORDER BY SSV.STUDENT_ID, SSV.SEMESTER ");
        sql.append("),");

        sql.append("CLASS_SCORE_VIEW AS (");
        sql.append("SELECT SMSV.STUDENT_ID,STD.FIRST_NAME, STD.LAST_NAME,");
        sql.append("STRING_AGG(SMSV.SEMESTER || '#' || SMSV.SEM_AVG,',') AS SEM_AVG_SCORE,");
        sql.append("SUM(CASE SMSV.SEMESTER WHEN 1 THEN ROUND(SMSV.SEM_AVG * SMSV.FIRST_SEM_RATE, 2) " +
                "WHEN 2 THEN ROUND(SMSV.SEM_AVG * SMSV.SECOND_SEM_RATE,2) " +
                "ELSE 0 END) AS FINAL_AVG ");
        sql.append("FROM SEMESTER_SCORE_VIEW SMSV ");
        sql.append("JOIN STUDENT STD ON STD.STUDENT_ID = SMSV.STUDENT_ID AND STD.IS_DELETED = FALSE ");
        sql.append("WHERE STD.CLASS_ID =:classId ");
        sql.append("GROUP BY SMSV.STUDENT_ID,STD.FIRST_NAME,STD.LAST_NAME ");
        sql.append("ORDER BY SMSV.STUDENT_ID ");
        sql.append("),");

        sql.append("CLASS_FIN_SCORE_VIEW AS (");
        sql.append("SELECT CLSV.STUDENT_ID, CLSV.FIRST_NAME, CLSV.LAST_NAME,CLSV.SEM_AVG_SCORE, CLSV.FINAL_AVG,");
        sql.append("CASE WHEN CLSV.FINAL_AVG < :badAvg THEN 0 " +
                "WHEN CLSV.FINAL_AVG >= :badAvg AND CLSV.FINAL_AVG < :avgAvg THEN 1 " +
                "WHEN CLSV.FINAL_AVG >= :avgAvg AND CLSV.FINAL_AVG < :goodAvg THEN 2 " +
                "WHEN CLSV.FINAL_AVG >= :goodAvg AND CLSV.FINAL_AVG < :veryGoodAvg THEN 3 " +
                "ELSE 4 " +
                "END AS S_RANK ");
        sql.append("FROM CLASS_SCORE_VIEW CLSV ");
        sql.append("ORDER BY CLSV.FINAL_AVG DESC ");
        sql.append(") ");
    }
}
