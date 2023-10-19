package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.model.Application;
import com.sanjar.trainingcenter.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/application")
public class ApplicationsController {
    private final ApplicationService applicationService;

    @GetMapping()
    private String showPage(Model model) {
        List<Application> applList = applicationService.findAll();

        model.addAttribute("countApplications", applList.size());
        model.addAttribute("applications", applList);

        return "dashboard/application";
    }

    @GetMapping("/update")
    private String updatePage() {
        applicationService.updateCache();
        return "redirect:/dashboard/application";
    }


    @PostMapping("/{id}")
    private String delete(@PathVariable long id) {
        applicationService.deleteById(id);
        applicationService.updateCache();
        return "redirect:/dashboard/application";
    }
}
