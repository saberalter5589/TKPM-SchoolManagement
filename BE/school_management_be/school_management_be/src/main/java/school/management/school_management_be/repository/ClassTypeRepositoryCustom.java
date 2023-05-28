package school.management.school_management_be.repository;

import school.management.school_management_be.dto.request.classtype.GetClassTypeRequest;

import java.util.List;

public interface ClassTypeRepositoryCustom {
    List<Object[]> searchClassType(GetClassTypeRequest request);
}
