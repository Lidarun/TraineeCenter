package com.sanjar.trainingcenter.controller;

import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping
    public String home(Model model,
                       @AuthenticationPrincipal OAuth2User oAuth2User,
                       @AuthenticationPrincipal UserDetails userDetails) {
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

        return "index";
    }

}
