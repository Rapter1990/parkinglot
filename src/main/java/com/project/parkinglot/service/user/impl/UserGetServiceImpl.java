package com.project.parkinglot.service.user.impl;

import com.project.parkinglot.exception.user.UserNotFoundException;
import com.project.parkinglot.model.User;
import com.project.parkinglot.model.mapper.user.UserEntityToUserMapper;
import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.service.auth.UserService;
import com.project.parkinglot.service.user.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGetServiceImpl implements UserGetService {

    private final UserService userService;

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    @Override
    public User getUserById(String userId) {

        final UserEntity userEntity = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userEntityToUserMapper.map(userEntity);
    }

    @Override
    public User getAdminById(final String adminId) {

        final UserEntity adminEntity = userService.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException(adminId));

        return userEntityToUserMapper.map(adminEntity);
    }

}
