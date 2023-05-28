package school.management.school_management_be.repository;

import school.management.school_management_be.dto.request.user.GetUserRequest;

import java.util.List;

public interface SUserRepositoryCustom {
    List<Object[]> searchUser(GetUserRequest request);
}
