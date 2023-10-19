package com.sanjar.trainingcenter.repository;

import com.sanjar.trainingcenter.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByState(boolean state);
}
