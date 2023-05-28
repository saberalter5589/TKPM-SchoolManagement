package school.management.school_management_be.service;

import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.user.LoginRequest;
import school.management.school_management_be.dto.response.user.LoginResponse;

import java.util.List;

public interface AuthenticationService {
    void validateUser(BaseRequest request, List<Long> userTypeList);
    LoginResponse login(LoginRequest request);
}
