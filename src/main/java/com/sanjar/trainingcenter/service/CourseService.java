package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.model.Course;

import java.util.List;

public interface CourseService extends CrudService<Course>{
    void deleteById(long id);
    void update(long id, Course updatedCourse);
    List<Course> updateCache();
    List<Course> findAllByUserAccess(String username);
}
