package school.management.school_management_be.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import school.management.school_management_be.dto.request.student.GetStudentRequest;
import school.management.school_management_be.repository.StudentRepositoryCustom;
import school.management.school_management_be.util.CommonUtil;
import school.management.school_management_be.util.QueryUtil;

import java.util.HashMap;
import java.util.List;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Object[]> searchStudent(GetStudentRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT \n" +
                "st.student_id,\n" +
                "st.class_id,\n" +
                "sc.class_code,\n" +
                "sc.class_name,\n" +
                "sc.max_student_num,\n" +
                "st.first_name,\n" +
                "st.last_name,\n" +
                "st.birth_date,\n" +
                "st.gender,\n" +
                "st.address,\n" +
                "st.email,\n" +
                "st.description,\n" +
                "st.note ");
        sql.append("FROM student st ");
        sql.append("LEFT JOIN s_class sc ON st.class_id = sc.class_id AND sc.is_deleted = false ");
        sql.append("WHERE st.is_deleted = false ");

        if(request.getStudentId() != null){
            sql.append("AND st.student_id =:id ");
            params.put("id", request.getStudentId());
        }

        if(request.getClassId() != null){
            sql.append("AND sc.class_id =:classId ");
            params.put("classId", request.getClassId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getFirstName())){
            sql.append("AND st.first_name LIKE :firstName ");
            params.put("firstName", "%" + request.getFirstName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getLastName())){
            sql.append("AND st.last_name LIKE :lastName ");
            params.put("lastName", "%" + request.getFirstName() + "%");
        }

        if(request.getGender() != null){
            sql.append("AND st.gender =:gender ");
            params.put("gender", request.getGender());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getEmail())){
            sql.append("AND st.email LIKE :email ");
            params.put("email", "%" + request.getEmail() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getAddress())){
            sql.append("AND st.address LIKE :address ");
            params.put("address", "%" + request.getAddress() + "%");
        }

        sql.append("ORDER BY st.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
