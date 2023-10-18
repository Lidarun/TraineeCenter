package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.model.Role;
import com.sanjar.trainingcenter.model.User;
import com.sanjar.trainingcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class MainDashController {
    private final UserService userService;

    @GetMapping()
    private String showPage(Model model) {
        List<User> userList = userService.findAll();
        userService.updateCache();

        model.addAttribute("countUsers", userList.size());
        model.addAttribute("users", userList);
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);

        return "dashboard/dashboard";
    }

    @GetMapping("/{rolePage}")
    private String showFilterPage(@PathVariable("rolePage") Role role,
                                  Model model) {
        System.out.println("ROLE: "+ role);
        List<User> userList = userService.findAllByRole(role);

        model.addAttribute("countUsers", userList.size());
        model.addAttribute("users", userList);
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);

        return "dashboard/filter/users-by-role";
    }

//    @GetMapping("/update/{id}")
//    private String updateUser(@PathVariable("id") long id,
//                              Model model) {
//        User user = userService.findById(id);
//
//        model.addAttribute("fromUser", user);
//        model.addAttribute("users", userService.findAll());
//        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);
//        model.addAttribute("roleA", Role.ROLE_SUPER_ADMIN);
//        model.addAttribute("roles", Role.values());
//
//        return "dashboard/edit-user";
//    }
//
//    @PostMapping("/update/{id}")
//    private String setRole(@PathVariable long id,
//                           @RequestParam("role") Role role) {
//        userService.changeRole(id, role);
//
//        return "redirect:/dashboard";
//    }
//
//    @PostMapping("/delete/{id}")
//    private String deleteUser(@PathVariable long id) {
//        userService.deleteByID(id);
//
//        return "redirect:/dashboard";
//    }
//
//
//    //Живой поиск
//    @ResponseBody
//    @GetMapping("/search")
//    public Optional<UserDto> searchUser(@RequestParam("query") String userInfo) {
//        List<UserDto> users = userService.findAllAsUserDto();
//
//        return users.stream().filter(user ->
//                user.getEmail().toLowerCase().contains(userInfo.toLowerCase()) ||
//                        user.getFullName().toLowerCase().contains(userInfo.toLowerCase())).findFirst();
//
//    }
}
