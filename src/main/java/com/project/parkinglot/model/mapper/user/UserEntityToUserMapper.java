package com.project.parkinglot.model.mapper.user;


import com.project.parkinglot.model.User;
import com.project.parkinglot.model.mapper.BaseMapper;
import com.project.parkinglot.security.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityToUserMapper extends BaseMapper<UserEntity, User> {

    @Override
    @Mapping(source = "vehicles", target = "vehicleList")
    User map(UserEntity source);

    static UserEntityToUserMapper initialize() {
        return Mappers.getMapper(UserEntityToUserMapper.class);
    }

}
