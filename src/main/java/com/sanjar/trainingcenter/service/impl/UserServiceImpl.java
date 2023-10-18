package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.dto.UserDto;
import com.sanjar.trainingcenter.mappers.EntityMapper;
import com.sanjar.trainingcenter.model.Role;
import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.repository.UserRepository;
import com.sanjar.trainingcenter.service.UserService;
import com.sanjar.trainingcenter.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"users"})
public class UserServiceImpl implements UserService, UserValidationService {
    private final UserRepository userRep;
    private final EntityMapper<User, UserDto> mapper;

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

    @Override
    public List<User> findAll() {
        return userRep.findAll();
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userRep.findAllByRolesContains(role);
    }

    @Override
    @Cacheable
    public List<UserDto> findAllAsUserDto() {
        List<User> users = userRep.findAll();
        return users.stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    @CachePut
    public List<UserDto> updateCache() {
        List<User> users = userRep.findAll();
        return users.stream().map(mapper::map).collect(Collectors.toList());
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
