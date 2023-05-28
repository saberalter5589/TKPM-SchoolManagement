package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.user.CreateUserRequest;
import school.management.school_management_be.dto.request.user.GetUserRequest;
import school.management.school_management_be.dto.response.user.CreateUserResponse;
import school.management.school_management_be.dto.response.user.GetUserResponse;

public interface SUserService {
    CreateUserResponse createUser(CreateUserRequest request);
    GetUserResponse searchUser(GetUserRequest request);
    void updateUser(Long userId, CreateUserRequest request);
    void deleteUser(Long userId);
}
