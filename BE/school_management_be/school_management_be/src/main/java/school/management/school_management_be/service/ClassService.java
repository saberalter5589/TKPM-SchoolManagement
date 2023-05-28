package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.sclass.CreateClassRequest;
import school.management.school_management_be.dto.request.sclass.GetClassRequest;
import school.management.school_management_be.dto.response.sclass.CreateClassResponse;
import school.management.school_management_be.dto.response.sclass.GetClassResponse;

public interface ClassService {
    CreateClassResponse createClass(CreateClassRequest request);
    GetClassResponse searchClass(GetClassRequest request);
    void updateClass(Long id, CreateClassRequest request);
    void deleteClass(Long id, BaseRequest request);
}
