package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.model.TrialUser;
import com.sanjar.trainingcenter.service.TrialUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/trial-user")
public class TrialUserController {

    private final TrialUserService trialUserService;

    @GetMapping
    public String showPage(Model model) {

        List<TrialUser> trialUsers = trialUserService.findAll();
        model.addAttribute("countTrialUsers", trialUsers.size());
        model.addAttribute("trialUsers", trialUsers);

        return "dashboard/trial-user";
    }

}
