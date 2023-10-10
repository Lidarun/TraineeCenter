package com.sanjar.trainingcenter.controller;

import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.service.UserService;
import com.sanjar.trainingcenter.service.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserValidationService validationService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    //    User registration
    @GetMapping("/registration")
    public String showRegistrationPage(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("userForm") @Valid User user,
                               BindingResult bindingResult, Model model){
        model.addAttribute(user);

        System.out.println(user);

        ObjectError errorEmail = validationService.existUserByEmail(user.getEmail());
        if (errorEmail != null) bindingResult.addError(errorEmail);

        ObjectError errorPassword = validationService.validPassword(user.getPassword());
        if (errorPassword != null) bindingResult.addError(errorPassword);

        if(bindingResult.hasErrors()) return "registration";

        userService.create(user);

        return "redirect:/login";
    }

    //    Logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logoutSuccess=true";
    }
}
