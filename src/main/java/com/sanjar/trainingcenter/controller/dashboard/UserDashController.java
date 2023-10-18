package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.dto.UserDto;
import com.sanjar.trainingcenter.exceptions.UserNotFoundException;
import com.sanjar.trainingcenter.model.Role;
import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/user")
public class UserDashController {
    private final UserService userService;

    @GetMapping()
    private String showPage(Model model) {
        List<UserDto> userList = userService.findAllAsUserDto();

        model.addAttribute("countUsers", userList.size());
        model.addAttribute("users", userList);
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);

        return "dashboard/dashboard";
    }

    @GetMapping("/update")
    private String updatePage() {
        userService.updateCache();
        return "redirect:/dashboard/user";
    }

    @GetMapping("/{rolePage}")
    private String showFilterPage(@PathVariable("rolePage") Role role,
                                  Model model) {
        List<User> userList = userService.findAllByRole(role);

        model.addAttribute("countUsers", userList.size());
        model.addAttribute("users", userList);
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);

        return "dashboard/filter/users-by-role";
    }

    @GetMapping("/update/{id}")
    private String updateUser(@PathVariable("id") long id,
                              Model model) throws UserNotFoundException {
        User user = userService.findById(id);

        model.addAttribute("fromUser", user);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);
        model.addAttribute("roleA", Role.ROLE_SUPER_ADMIN);
        model.addAttribute("roles", Role.values());

        return "dashboard/edit-user";
    }

    @PostMapping("/update/{id}")
    private String setRole(@PathVariable long id,
                           @RequestParam("role") Role role) throws UserNotFoundException {
        userService.changeRole(id, role);
        userService.updateCache();

        return "redirect:/dashboard/user";
    }

    @PostMapping("/delete/{id}")
    private String deleteUser(@PathVariable long id) throws UserNotFoundException {
        userService.deleteByID(id);
        userService.updateCache();

        return "redirect:/dashboard/user";
    }


//    //Живой поиск
    @ResponseBody
    @GetMapping("/search")
    public Optional<UserDto> searchUser(@RequestParam("query") String userInfo) {
        List<UserDto> users = userService.findAllAsUserDto();

        return users.stream().filter(user ->
                user.getEmail().toLowerCase().contains(userInfo.toLowerCase()) ||
                        user.getFirstName().toLowerCase().contains(userInfo.toLowerCase()) ||
                        user.getLastName().toLowerCase().contains(userInfo.toLowerCase())).findFirst();
    }
}
