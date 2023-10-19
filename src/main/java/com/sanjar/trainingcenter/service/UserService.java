package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.dto.UserDto;
import com.sanjar.trainingcenter.exceptions.UserNotFoundException;
import com.sanjar.trainingcenter.model.Role;
import com.sanjar.trainingcenter.model.User;

import java.util.List;

public interface UserService extends CrudService<User> {
    User findByEmail(String email);
    List<User> findAllByRole(Role role);
    List<UserDto> findAllAsUserDto();
    List<UserDto> updateCache();
    void changeRole(long id, Role role) throws UserNotFoundException;
}
