package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.model.Course;
import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.repository.CourseRepository;
import com.sanjar.trainingcenter.service.CourseService;
import com.sanjar.trainingcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRep;
    private final UserService userService;

    @Override
    public void create(Course course) {
        courseRep.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRep.findAll();
    }

    @Override
    public void deleteById(long id) {
        if (courseRep.findById(id).isPresent())
            courseRep.deleteById(id);
    }

    @Override
    public Course findById(long id) {
        return courseRep.findById(id).orElse(null);
    }

    @Override
    public void update(long id, Course updatedCourse) {
        if (courseRep.findById(id).isPresent())
            courseRep.save(updatedCourse);
    }

    //Получение списка курсов, с установкой доступа для конкретного пользователя
    @Override
    public List<Course> findAllByUserAccess(String email) {
        List<Course> courses = courseRep.findAllByState(true);

        Optional<User> userOptional = Optional.ofNullable(userService.findByEmail(email));

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Set<Course> userCourses = user.getCourses();

            //Ограничение доступа к курсам
            return courses.stream().peek(course ->
                    userCourses.forEach(userCourse -> {
                                if (userCourse.getId() == course.getId()) course.setAccess(true);
                            }
                    )).collect(Collectors.toList());
        }

        return null;
    }
}
