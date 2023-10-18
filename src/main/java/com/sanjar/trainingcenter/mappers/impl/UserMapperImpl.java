package com.sanjar.trainingcenter.mappers.impl;

import com.sanjar.trainingcenter.dto.UserDto;
import com.sanjar.trainingcenter.mappers.EntityMapper;
import com.sanjar.trainingcenter.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements EntityMapper<User, UserDto> {

    @Override
    public UserDto map(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), "0/0");
    }
}