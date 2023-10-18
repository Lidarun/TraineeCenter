package com.sanjar.trainingcenter.dto;

import com.sanjar.trainingcenter.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String result;
    private Set<Role> roles;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", fullName='" + firstName + lastName + '\'' +
                ", email='" + email + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

}
