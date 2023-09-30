package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.model.User;

public interface UserService {
    User findByEmail(String email);
}
