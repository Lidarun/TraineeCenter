package com.sanjar.trainingcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String result;

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
