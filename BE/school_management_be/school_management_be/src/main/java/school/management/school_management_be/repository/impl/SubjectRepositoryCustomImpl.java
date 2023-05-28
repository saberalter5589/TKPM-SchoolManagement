package school.management.school_management_be.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import school.management.school_management_be.dto.request.subject.GetSubjectRequest;
import school.management.school_management_be.repository.SubjectRepositoryCustom;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.QueryUtil;

import java.util.HashMap;
import java.util.List;

public class SubjectRepositoryCustomImpl implements SubjectRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchSubject(GetSubjectRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sbj.subject_id, sbj.subject_code, sbj.subject_name, sbj.avg_score, sbj.description, sbj.note ");
        sql.append("FROM subject sbj ");
        sql.append("WHERE sbj.is_deleted = false ");

        if(request.getSubjectId() != null){
            sql.append("AND sbj.subject_id =:id ");
            params.put("id", request.getSubjectId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getSubjectCode())){
            sql.append("AND sbj.subject_code LIKE :subjectCode ");
            params.put("subjectCode", "%" + request.getSubjectCode() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getSubjectName())){
            sql.append("AND sbj.subject_code LIKE :subjectName ");
            params.put("subjectName", "%" + request.getSubjectName() + "%");
        }
        sql.append("ORDER BY sbj.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
