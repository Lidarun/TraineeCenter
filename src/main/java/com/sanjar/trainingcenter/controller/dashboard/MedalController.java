package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.model.Medal;
import com.sanjar.trainingcenter.service.MedalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/medal")
public class MedalController {

    private final MedalService medalService;

    @GetMapping()
    private String showPage(Model model) {
        List<Medal> medals = medalService.findAll();

        model.addAttribute("formMedal", new Medal());
        model.addAttribute("medals", medals);

        return "dashboard/medal";
    }

    @GetMapping("/update")
    private String updatePage() {
        medalService.updateCache();
        return "redirect:/dashboard/medal";
    }

    @GetMapping("/{id}")
    private String editCourse(@PathVariable("id") long id,
                              Model model) {
        List<Medal> medals = medalService.findAll();
        Medal medal = medalService.findById(id);

        model.addAttribute("medals", medals);

        if (medal != null) {
            model.addAttribute("formMedal", medal);
            return "dashboard/edit-medal";

        }else
            return "redirect:/dashboard/medal";
    }

    @PostMapping
    private String createCourse(@ModelAttribute("formMedal") Medal medal) {
        medalService.create(medal);
        medalService.updateCache();
        return "redirect:/dashboard/medal";
    }

    @PostMapping("/{id}")
    public String updateCourse(@PathVariable("id") long id,
                               @ModelAttribute("formCourse") Medal medal) {
        medalService.update(id, medal);
        medalService.updateCache();
        return "redirect:/dashboard/medal";
    }

    @PostMapping("/delete/{id}")
    private String deleteCourse(@PathVariable("id") long id) {
        medalService.deleteById(id);
        medalService.updateCache();

        return "redirect:/dashboard/medal";
    }
}
