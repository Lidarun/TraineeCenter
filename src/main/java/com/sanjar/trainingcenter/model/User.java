package com.sanjar.trainingcenter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_users")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Укажите ваше имя")
    private String firstName;
    @NotEmpty(message = "Укажите вашу фамилию")
    private String lastName;

    @NotEmpty(message = "Обязательное поле")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(unique = true)
    @NotEmpty(message = "Обязательное поле")
    @Email(message = "Пожалуйста, введите действительный e-mail адрес")
    private String email;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.ROLE_USER;


    @Override
    public String toString() {
        return "User| " +
                " id: " + id +
                "| firstName: " + firstName + '\'' +
                "| lastName: " + lastName + '\'' +
                "| email: " + email + '\'' +
                "| password: " + password + '\'' +
                "| confirmPassword: " + confirmPassword + '\'' +
                "| role: " + role;
    }
}
