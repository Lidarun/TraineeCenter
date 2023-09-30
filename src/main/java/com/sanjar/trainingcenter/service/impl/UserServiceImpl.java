package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.repository.UserRepository;
import com.sanjar.trainingcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRep;

    @Override
    public User findByEmail(String email) {
        return userRep.findByEmail(email).orElse(null);
    }
}
