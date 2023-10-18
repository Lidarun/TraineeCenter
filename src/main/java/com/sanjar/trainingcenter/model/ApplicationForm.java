package com.sanjar.trainingcenter.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_applications")
public class ApplicationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phone;

    private String course;
}
