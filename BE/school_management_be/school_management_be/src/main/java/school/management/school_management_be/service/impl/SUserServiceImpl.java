package school.management.school_management_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.management.school_management_be.common.UserRole;
import school.management.school_management_be.dto.obj.MessageInfo;
import school.management.school_management_be.dto.obj.UserDto;
import school.management.school_management_be.dto.request.user.CreateUserRequest;
import school.management.school_management_be.dto.request.user.GetUserRequest;
import school.management.school_management_be.dto.response.user.CreateUserResponse;
import school.management.school_management_be.dto.response.user.GetUserResponse;
import school.management.school_management_be.entity.SUser;
import school.management.school_management_be.exception.DataDuplicatedException;
import school.management.school_management_be.exception.NoDataFoundException;
import school.management.school_management_be.mapper.EntityDxo;
import school.management.school_management_be.mapper.UserDxo;
import school.management.school_management_be.repository.SUserRepository;
import school.management.school_management_be.service.SUserService;
import school.management.school_management_be.util.MessageUtil;

import java.util.List;

@Service
public class SUserServiceImpl implements SUserService {

    @Autowired
    SUserRepository sUserRepository;

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        // Check if user (username) exist or not
        SUser user = sUserRepository.findByUserName(request.getUserName());
        if(user != null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "username");
            throw new DataDuplicatedException(messageInfo);
        }

        SUser newUser = UserDxo.mapFromRequestToEntity(request);
        newUser.setURole(UserRole.STAFF);
        newUser.setIsDeleted(false);
        EntityDxo.preCreate(0L, newUser);
        sUserRepository.save(newUser);

        return new CreateUserResponse();
    }

    @Override
    public GetUserResponse searchUser(GetUserRequest request) {
        List<Object[]> dbUserList = sUserRepository.searchUser(request);
        List<UserDto> userDtoList = UserDxo.mapFromDbObjListToUserDtoList(dbUserList);
        GetUserResponse response = new GetUserResponse();
        response.setUserDtoList(userDtoList);
        return response;
    }

    @Override
    @Transactional
    public void updateUser(Long userId, CreateUserRequest request) {
        Long authId = request.getAuthentication().getUserId();

        SUser user = sUserRepository.findByUserId(userId);
        if(user == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "userId");
            throw new NoDataFoundException(messageInfo);
        }
        user.setPassword(request.getPassword());
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setEmail(request.getEmail());
        EntityDxo.preUpdate(authId, user);
        sUserRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        SUser user = sUserRepository.findByUserId(userId);
        if(user == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "userId");
            throw new NoDataFoundException(messageInfo);
        }
        user.setIsDeleted(true);
        EntityDxo.preUpdate(0L, user);
        sUserRepository.save(user);
    }
}
