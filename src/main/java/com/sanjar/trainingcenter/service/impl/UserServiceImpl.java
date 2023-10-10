package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.repository.UserRepository;
import com.sanjar.trainingcenter.service.UserService;
import com.sanjar.trainingcenter.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserValidationService {
    private final UserRepository userRep;

    @Override
    public void create(User user) {
        String password = new BCryptPasswordEncoder()
                .encode(user.getPassword());
        user.setPassword(password);
        userRep.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRep.findByEmail(email).orElse(null);
    }


//  VALIDATION
    @Override
    public ObjectError existUserByEmail(String email) {
        boolean exist = userRep.existsByEmail(email);

        ObjectError error = new ObjectError("global",
                "• Пользователь c почтой '" +email+ "' уже существует");

        if (userRep.existsByEmail(email))
            return error;

        return null;
    }


    @Override
    public ObjectError validPassword(String password) {
        if (password.isEmpty()) return null;

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";


        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches())
            return new ObjectError("global",
                    "• Пароль должен содержать не менее 8-ми символов, " +
                            "\nв том числе цифры, прописные и строчные буквы");

        return null;
    }
}
