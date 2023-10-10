package com.sanjar.trainingcenter.service;

import org.springframework.validation.ObjectError;

public interface UserValidationService {
    ObjectError existUserByEmail(String email);
    ObjectError validPassword(String password);
}
