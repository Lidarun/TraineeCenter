package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.model.User;

public interface UserService {
    void create(User user);
    User findByEmail(String email);
}
