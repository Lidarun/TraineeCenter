package com.sanjar.trainingcenter.repository;

import com.sanjar.trainingcenter.model.Course;
import com.sanjar.trainingcenter.model.Module;
import com.sanjar.trainingcenter.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllByCourse(Course course);
    Module findByQuestions(Question question);
}
