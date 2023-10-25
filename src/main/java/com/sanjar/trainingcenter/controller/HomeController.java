package com.sanjar.trainingcenter.controller;

import com.sanjar.trainingcenter.model.Application;
import com.sanjar.trainingcenter.model.TrialUser;
import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.service.ApplicationService;
import com.sanjar.trainingcenter.service.TrialUserService;
import com.sanjar.trainingcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final ApplicationService applicationService;
    private final TrialUserService trialUserService;

    @GetMapping
    public String home(Model model,
                       @AuthenticationPrincipal OAuth2User oAuth2User,
                       @AuthenticationPrincipal UserDetails userDetails) {
        List<TrialUser> rating = trialUserService.getTopStudentsByResult(10);
        model.addAttribute("rating", rating);
        model = getUserName(model, oAuth2User, userDetails);

        return "index";
    }

    @GetMapping("/ort-course")
    public String shopOrtCoursePage(Model model,
                       @AuthenticationPrincipal OAuth2User oAuth2User,
                       @AuthenticationPrincipal UserDetails userDetails) {
        model = getUserName(model, oAuth2User, userDetails);

        return "ort-course";
    }

    @GetMapping("/computer-course")
    public String shopPcCoursePage(Model model,
                                    @AuthenticationPrincipal OAuth2User oAuth2User,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        model = getUserName(model, oAuth2User, userDetails);

        return "computer-course";
    }


    @PostMapping("/application")
    public ResponseEntity<?> getApplication(@RequestBody Application application) {
        applicationService.create(application);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }


    private Model getUserName(Model model,
                              OAuth2User oAuth2User, UserDetails userDetails) {

        if (oAuth2User != null) {
            String name = oAuth2User.getAttribute("name");
            model.addAttribute("name", name);
        }

        if (userDetails != null) {
            User user = userService.findByEmail(userDetails.getUsername());
            if (user != null) {
                model.addAttribute("name", user.getFirstName() + " "
                        + user.getLastName());
            }
        }

        return model;
    }
}

