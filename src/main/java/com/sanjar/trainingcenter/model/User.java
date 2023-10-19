package com.sanjar.trainingcenter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_users_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name = "tb_users_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    @Override
    public String toString() {
        return "User| " +
                " id: " + id +
                "| firstName: " + firstName + '\'' +
                "| lastName: " + lastName + '\'' +
                "| email: " + email + '\'' +
                "| password: " + password + '\'' +
                "| confirmPassword: " + confirmPassword + '\'' +
                "| role: " + roles;
    }


    public void addCourse(Course course) {
        courses.add(course);
        course.getUsers().add(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.getUsers().remove(this);
    }
}
