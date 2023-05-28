package school.management.school_management_be.repository;

import school.management.school_management_be.dto.request.classtype.GetClassTypeRequest;
import school.management.school_management_be.dto.request.subject.GetSubjectRequest;

import java.util.List;

public interface SubjectRepositoryCustom {
    List<Object[]> searchSubject(GetSubjectRequest request);
}
