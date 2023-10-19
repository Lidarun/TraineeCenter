package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.model.Course;
import com.sanjar.trainingcenter.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/course")
public class CourseDashController {
    private final CourseService service;

    @GetMapping
    private String showPage(Model model) {
        List<Course> courseList = service.findAll();

        model.addAttribute("formCourse", new Course());
        model.addAttribute("courses", courseList);

        return "dashboard/course";
    }

//    @GetMapping("/edit/{id}")
//    private String editCourse(@PathVariable("id") long id,
//                              Model model) {
//        List<Course> courseList = service.findAll();
//        Course course = service.findById(id);
//
//        model.addAttribute("courses", courseList);
//
//        if (course != null) {
//            model.addAttribute("formCourse", course);
//            return "dashboard/edit-course";
//
//        }else
//            return "redirect:/courses";
//    }

    @PostMapping
    private String createCourse(@ModelAttribute("formCourse") Course course) {
        service.create(course);
        return "redirect:/dashboard/course";
    }

//    @PostMapping("/edit/{id}")
//    public String updateCourse(@PathVariable("id") long id,
//                               @ModelAttribute("formCourse") Course updatedCourse) {
//        service.update(id, updatedCourse);
//
//        return "redirect:/courses";
//    }
//
//    @PostMapping("/{id}")
//    private String deleteCourse(@PathVariable("id") long id) {
//        service.deleteById(id);
//        return "redirect:/courses";
//    }
}
