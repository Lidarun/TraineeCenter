package com.sanjar.trainingcenter.controller.dashboard;

import com.sanjar.trainingcenter.model.Module;
import com.sanjar.trainingcenter.model.Question;
import com.sanjar.trainingcenter.service.CourseService;
import com.sanjar.trainingcenter.service.ModuleService;
import com.sanjar.trainingcenter.service.QuestionService;
import com.sanjar.trainingcenter.service.QuestionValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/question")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionValidationService service;
    private final CourseService courseService;
    private final ModuleService moduleService;

    @GetMapping("/{pageNum}")
    public String showPage(Model model,
                           @PathVariable(name = "pageNum") int pageNum) {
        model.addAttribute("formQuestion", new Question());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("modules", moduleService.findAll());

        Page<Question> page = questionService.getItems(PageRequest.of(pageNum, 15));

        List<Question> list = page.getContent();
        model.addAttribute("listQuestions", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        return "dashboard/question";
    }

    //   Возврщаетя список модулей курсаа в медод AJAX
    @ResponseBody
    @GetMapping("/loadModules")
    public ResponseEntity<List<Module>> loadModulesForCourse(@RequestParam Long courseId) {
        List<Module> modules = moduleService.getModulesByCourseId(courseId);
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    @PostMapping("/{pageNum}")
    public String createQuestion(@PathVariable("pageNum") int pageNum,
                                 @ModelAttribute("formQuestion") @Valid Question question,
                                 BindingResult result, Model model) {

        System.out.println(question);

        Page<Question> page = questionService.getItems(PageRequest.of(pageNum, 5));

        List<Question> list = page.getContent();
        model.addAttribute("listQuestions", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("modules", moduleService.findAll());
        model.addAttribute("formQuestion", question);
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("modules", moduleService.findAll());

        ObjectError error = service.checkFields(question);
        if (error != null)
            result.addError(error);

        if (result.hasErrors())
            return "dashboard/question";

        question.setCorrectAnswer(question.getCorrectAnswer() - 1);

        questionService.create(question);

        return "redirect:/dashboard/question/" + pageNum;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Page<Question> page = questionService.getItems(PageRequest.of(0, 5));

        List<Question> list = page.getContent();
        model.addAttribute("listQuestions", list);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("modules", moduleService.findAll());

        Question question = questionService.findById(id);

        if (question != null) {
            model.addAttribute("formQuestion", question);

            return "dashboard/edit-question";

        } else
            return "redirect:/dashboard/question/0";
    }


    @PostMapping("/edit/{id}")
    public String updateQuestion(@PathVariable("id") int id,
                                 @ModelAttribute("formQuestion") Question updatedQuestion) {
        Question questionOptional = questionService.findById(id);
        if (questionOptional != null) {
            updatedQuestion.setCorrectAnswer(updatedQuestion.getCorrectAnswer() - 1);
            questionService.create(updatedQuestion);
        }

        return "redirect:/dashboard/question/0";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") int id) {
        questionService.deleteById(id);
        return "redirect:/dashboard/question/0";
    }
}
