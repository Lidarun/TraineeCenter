package com.sanjar.trainingcenter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Transient
    private String confirmPassword;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
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
