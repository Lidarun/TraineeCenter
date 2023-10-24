package com.sanjar.trainingcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrialUserRequest {
    private long id;
    private String fullName;
    private String phone;
    private String token;
    private int result = 0;
}
