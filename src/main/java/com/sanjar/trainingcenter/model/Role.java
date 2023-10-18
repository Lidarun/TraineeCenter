package com.sanjar.trainingcenter.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.function.Predicate;

@Getter
public enum Role implements GrantedAuthority, Predicate<Role> {
    ROLE_USER("Студент"),
    ROLE_TEACHER("Учитель"),
    ROLE_ADMIN("Помощник"),
    ROLE_SUPER_ADMIN("Администрация");

    public final String name;
    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    @Override
    public boolean test(Role role) {
        return role.equals(ROLE_USER) || role.equals(ROLE_SUPER_ADMIN) || role.equals(ROLE_TEACHER) || role.equals(ROLE_ADMIN);
    }
}
