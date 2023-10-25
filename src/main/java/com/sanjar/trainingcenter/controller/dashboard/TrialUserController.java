package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.model.Course;
import com.sanjar.trainingcenter.model.TrialUser;
import com.sanjar.trainingcenter.service.TrialUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/trial-user")
public class TrialUserController {

    private final TrialUserService trialUserService;

    @GetMapping
    public String showPage(Model model) {

        List<TrialUser> trialUsers = trialUserService.findAll();
        model.addAttribute("formTrialUser", new TrialUser());
        model.addAttribute("countTrialUsers", trialUsers.size());
        model.addAttribute("trialUsers", trialUsers);

        return "dashboard/trial-user";
    }

    @GetMapping("/update")
    private String updatePage() {
        trialUserService.updateCache();
        return "redirect:/dashboard/trial-user";
    }

    @GetMapping("/{id}")
    private String editCourse(@PathVariable("id") long id,
                              Model model) {
        List<TrialUser> trialUsers = trialUserService.findAll();
        model.addAttribute("trialUsers", trialUsers);

        TrialUser trialUser = trialUserService.findById(id);

        if (trialUser != null) {
            model.addAttribute("formTrialUser", trialUser);
            return "dashboard/edit-trial-user";

        }else
            return "redirect:/dashboard/trial-user";
    }

    @PostMapping
    public String createTrialUser(@ModelAttribute("formTrialUser") TrialUser trialUser) {
        trialUserService.create(trialUser);
        trialUserService.updateCache();

        return "redirect:/dashboard/trial-user";
    }

    @PostMapping("/{id}")
    public String updateCourse(@PathVariable("id") long id,
                               @ModelAttribute("formTrialUser") TrialUser trialUser) {
        trialUserService.update(id, trialUser);
        trialUserService.updateCache();
        return "redirect:/dashboard/trial-user";
    }


    @PostMapping("/delete/{id}")
    public String deleteTrialUser(@PathVariable long id) {
        trialUserService.deleteById(id);
        trialUserService.updateCache();

        return "redirect:/dashboard/trial-user";
    }
}
