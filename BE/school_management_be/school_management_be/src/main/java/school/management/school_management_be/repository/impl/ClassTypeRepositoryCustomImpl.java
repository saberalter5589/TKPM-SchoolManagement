package school.management.school_management_be.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import school.management.school_management_be.dto.request.classtype.GetClassTypeRequest;
import school.management.school_management_be.dto.request.user.GetUserRequest;
import school.management.school_management_be.repository.ClassTypeRepositoryCustom;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.QueryUtil;

import java.util.HashMap;
import java.util.List;

public class ClassTypeRepositoryCustomImpl implements ClassTypeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchClassType(GetClassTypeRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ct.class_type_id, ct.class_index, ct.class_type_code, ct.class_type_name, ct.description, ct.note ");
        sql.append("FROM class_type ct ");
        sql.append("WHERE ct.is_deleted = false ");

        if(request.getClassTypeId() != null){
            sql.append("AND ct.class_type_id =:id ");
            params.put("id", request.getClassTypeId());
        }

        if(request.getClassIndex() != null){
            sql.append("AND ct.class_index =:classIndex ");
            params.put("classIndex", request.getClassIndex());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getClassTypeCode())){
            sql.append("AND ct.class_type_code LIKE :classTypeCode ");
            params.put("classTypeCode", "%" + request.getClassTypeCode() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getClassTypeName())){
            sql.append("AND ct.class_type_name LIKE :classTypeName ");
            params.put("classTypeName", "%" + request.getClassTypeName() + "%");
        }

        sql.append("ORDER BY ct.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
