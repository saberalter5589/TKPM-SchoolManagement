package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.management.school_management_be.dto.obj.AuthDto;
import school.management.school_management_be.dto.obj.MessageInfo;
import school.management.school_management_be.dto.obj.UserDto;
import school.management.school_management_be.dto.request.BaseRequest;
import school.management.school_management_be.dto.request.user.LoginRequest;
import school.management.school_management_be.dto.response.user.LoginResponse;
import school.management.school_management_be.entity.SUser;
import school.management.school_management_be.exception.UnAuthorizationException;
import school.management.school_management_be.repository.SUserRepository;
import school.management.school_management_be.service.AuthenticationService;
import school.management.school_management_be.util.MessageUtil;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private SUserRepository sUserRepository;

    @Override
    @Transactional
    public void validateUser(BaseRequest request, List<Long> userTypeList) {
        AuthDto authDto = request.getAuthentication();
        SUser sUser =  sUserRepository.findByUserIdAndPassword(authDto.getUserId(), authDto.getPassword());
        if(sUser == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        if(!userTypeList.contains(sUser.getURole())){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        SUser user = sUserRepository.findByUserNameAndPassword(request.getUserName(), request.getPassword());
        if(user == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setPassword(user.getPassword());
        dto.setRoleId(user.getURole());

        LoginResponse response = new LoginResponse();
        response.setUser(dto);
        return response;
    }
}
