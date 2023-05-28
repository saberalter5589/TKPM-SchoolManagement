package school.management.school_management_be.repository;

import school.management.school_management_be.dto.request.sclass.GetClassRequest;

import java.util.List;

public interface ClassRepositoryCustom {
    List<Object[]> searchClass(GetClassRequest request);
}
