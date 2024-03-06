package com.project.parkinglot.model.mapper.user;

import com.project.parkinglot.model.User;
import com.project.parkinglot.model.mapper.BaseMapper;
import com.project.parkinglot.security.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserToUserEntityMapper extends BaseMapper<User, UserEntity> {

    @Override
    @Mapping(source = "vehicleList", target = "vehicles")
    UserEntity map(User source);

    static UserToUserEntityMapper initialize() {
        return Mappers.getMapper(UserToUserEntityMapper.class);
    }

}
