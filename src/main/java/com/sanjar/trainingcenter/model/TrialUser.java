package com.sanjar.trainingcenter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@ToString
@Getter @Setter
@Table(name = "tb_trial_users")
public class TrialUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;
    private String phone;
    private String token;
    private String medalImage;
    private int result = 0;
}
