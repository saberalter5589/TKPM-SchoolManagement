package school.management.school_management_be.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import school.management.school_management_be.dto.request.user.GetUserRequest;
import school.management.school_management_be.repository.SUserRepositoryCustom;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.QueryUtil;

import java.util.HashMap;
import java.util.List;

public class SUserRepositoryCustomImpl implements SUserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchUser(GetUserRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT su.user_id, su.u_role, su.user_name, su.first_name, su.last_name, su.email ");
        sql.append("FROM s_user su ");
        sql.append("WHERE su.is_deleted = false ");

        if(request.getUserId() != null){
            sql.append("AND su.user_id =:id ");
            params.put("id", request.getUserId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getUserName())){
            sql.append("AND su.user_name LIKE :userName ");
            params.put("userName", "%" + request.getUserName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getFirstName())){
            sql.append("AND su.first_name LIKE :firstName ");
            params.put("firstName", "%" + request.getFirstName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getLastName())){
            sql.append("AND su.last_name LIKE :lastName ");
            params.put("lastName", "%" + request.getLastName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getEmail())){
            sql.append("AND su.email LIKE :email ");
            params.put("email", "%" + request.getEmail() + "%");
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
