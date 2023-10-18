package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.model.SecurityUser;
import com.sanjar.trainingcenter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository rep;

//    У пользователя нет username, поэтому поиск происходит по email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return rep
                .findByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found (email): " + email));
    }
}
