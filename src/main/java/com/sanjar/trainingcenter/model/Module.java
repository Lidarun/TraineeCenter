package com.sanjar.trainingcenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_modules")
public class Module implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String name;

    private boolean state = false;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, mappedBy = "module")
    private List<Question> questions;

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER,
             cascade = CascadeType.REMOVE)
    @JoinColumn(name = "course_id")
    private Course course;

    @Transient
    private String userResult = "-";


    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", course=" + course.getName() +
                ", userResult='" + userResult + '\'' +
                '}';
    }
}
