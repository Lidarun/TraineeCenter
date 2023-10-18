package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.dto.UserDto;
import com.sanjar.trainingcenter.model.Role;
import com.sanjar.trainingcenter.model.User;

import java.util.List;

public interface UserService {
    void create(User user);
    User findByEmail(String email);
    List<User> findAll();
    List<User> findAllByRole(Role role);
    List<UserDto> findAllAsUserDto();
    List<UserDto> updateCache();
}
