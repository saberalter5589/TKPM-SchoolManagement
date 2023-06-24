package school.management.school_management_be.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import school.management.school_management_be.dto.request.sclass.GetClassRequest;
import school.management.school_management_be.repository.ClassRepositoryCustom;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.QueryUtil;

import java.util.HashMap;
import java.util.List;

public class ClassRepositoryCustomImpl implements ClassRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Object[]> searchClass(GetClassRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n" +
                "sc.class_id,\n" +
                "sc.class_type_id,\n" +
                "ct.class_index,\n" +
                "ct.class_type_code,\n" +
                "ct.class_type_name,\n" +
                "sc.class_code,\n" +
                "sc.class_name,\n" +
                "sc.max_student_num,\n" +
                "sc.description,\n" +
                "sc.note, std_info.std_count ");
        sql.append("FROM s_class sc ");
        sql.append("LEFT JOIN class_type ct ON sc.class_type_id = ct.class_type_id AND ct.is_deleted = false ");
        sql.append("LEFT JOIN (");
        sql.append("SELECT std.class_id, COUNT(std.student_id) as std_count " +
                "FROM student std " +
                "WHERE std.is_deleted = false " +
                "GROUP BY std.class_id ");
        sql.append(") as std_info ON sc.class_id = std_info.class_id ");
        sql.append("WHERE sc.is_deleted = false ");

        if(request.getClassId() != null){
            sql.append("AND sc.class_id =:id ");
            params.put("id", request.getClassId());
        }

        if(request.getClassTypeId() != null){
            sql.append("AND ct.class_type_id =:classTypeId ");
            params.put("classTypeId", request.getClassTypeId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getClassCode())){
            sql.append("AND sc.class_type_code LIKE :classCode ");
            params.put("classCode", "%" + request.getClassCode() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getClassName())){
            sql.append("AND sc.class_type_name LIKE :className ");
            params.put("className", "%" + request.getClassName() + "%");
        }

        sql.append("ORDER BY sc.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
