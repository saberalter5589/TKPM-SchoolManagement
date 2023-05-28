package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.classtype.CreateClassTypeRequest;
import school.management.school_management_be.dto.request.classtype.GetClassTypeRequest;
import school.management.school_management_be.dto.response.classtype.CreateClassTypeResponse;
import school.management.school_management_be.dto.response.classtype.GetClassTypeResponse;

public interface ClassTypeService {
    CreateClassTypeResponse createClassType(CreateClassTypeRequest request);
    GetClassTypeResponse searchClassType(GetClassTypeRequest request);
    void updateClassType(Long id, CreateClassTypeRequest request);
    void deleteClassType(Long id);
}
