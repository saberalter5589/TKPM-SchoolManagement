package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.subject.CreateSubjectRequest;
import school.management.school_management_be.dto.request.subject.GetSubjectRequest;
import school.management.school_management_be.dto.response.subject.CreateSubjectResponse;
import school.management.school_management_be.dto.response.subject.GetSubjectResponse;

public interface SubjectService {
    CreateSubjectResponse createSubject(CreateSubjectRequest request);
    GetSubjectResponse searchSubject(GetSubjectRequest request);
    void updateSubject(Long id, CreateSubjectRequest request);
    void deleteSubject(Long id);
}
