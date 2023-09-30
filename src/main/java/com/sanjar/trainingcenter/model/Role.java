package com.sanjar.trainingcenter.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.function.Predicate;

public enum Role implements GrantedAuthority, Predicate<Role> {
    ROLE_USER("Студент"),
    ROLE_TEACHER("Учитель"),
    ROLE_ADMIN("Администратор");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    @Override
    public boolean test(Role role) {
        return role.equals(ROLE_USER) || role.equals(ROLE_TEACHER) || role.equals(ROLE_ADMIN);
    }
}
