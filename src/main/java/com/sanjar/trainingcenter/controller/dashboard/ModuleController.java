package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.model.Course;
import com.sanjar.trainingcenter.model.Module;
import com.sanjar.trainingcenter.service.CourseService;
import com.sanjar.trainingcenter.service.ModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/module")
public class ModuleController {
    private final CourseService courseService;
    private final ModuleService moduleService;

    @GetMapping
    private String showPage(Model model) {
        List<Course> courses = courseService.findAll();

        model.addAttribute("formModule", new Module());
        model.addAttribute("courses", courses);
        model.addAttribute("modules", moduleService.findAll());

        return "dashboard/module";
    }

    @GetMapping("/update")
    public String updatePage() {
        moduleService.updateCache();
        return "redirect:/dashboard/module";
    }

    @GetMapping("/{id}")
    private String editCourse(@PathVariable("id") long id,
                              Model model) {
        List<Course> courses = courseService.findAll();
        Module module = moduleService.findById(id);

        model.addAttribute("formModule", module);
        model.addAttribute("courses", courses);
        model.addAttribute("modules", moduleService.findAll());

        return "dashboard/edit-module";
    }

    @PostMapping
    private String createModule(@ModelAttribute("formModule") @Valid Module module,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAll());
            return "dashboard/module";
        }

        moduleService.create(module);

        return "redirect:/dashboard/module";
    }

    @PostMapping("/{id}")
    public String updateCourse(@PathVariable("id") long id,
                               @ModelAttribute("formModule") Module updatedModule) {

        moduleService.update(id, updatedModule);

        return "redirect:/dashboard/module";
    }

    @PostMapping("/delete/{id}")
    private String deleteCourse(@PathVariable("id") long id) {

        moduleService.deleteById(id);

        return "redirect:/dashboard/module";
    }
}
