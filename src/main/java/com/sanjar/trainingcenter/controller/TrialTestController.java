package com.sanjar.trainingcenter.controller;

import com.sanjar.trainingcenter.dto.PromoCodeRequest;
import com.sanjar.trainingcenter.dto.QuestionDto;
import com.sanjar.trainingcenter.model.Course;
import com.sanjar.trainingcenter.model.TrialUser;
import com.sanjar.trainingcenter.service.QuestionService;
import com.sanjar.trainingcenter.service.TrialUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trial-test")
public class TrialTestController {

    private final QuestionService questionService;
    private final TrialUserService trialUserService;
//    private final ResultHandler resultHandler;

    @GetMapping
    private String showPage(Model model) {
        return "trial-test";
    }

    @GetMapping("/ort")
    public ResponseEntity<List<QuestionDto>> getTrialQuestions() {
        List<QuestionDto> questions = questionService.findAllByModuleID(999);
        List<String> options = new ArrayList<>();
        options.add("dafdsfads");
        options.add("dafdsfads");
        options.add("dafdsfads");
        QuestionDto dto = new QuestionDto(1, "asdfasdf", "asdfasdfasdf", options);
        QuestionDto dto2 = new QuestionDto(2, "asdfasdf", "asdfasdfasdf", options);

        questions.add(dto);
        questions.add(dto2);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

//    @PostMapping("/ort") //TODO
//    public ResponseEntity<?> getTrialTestResults(@ResponseBody ) {
//
//    }

    @ResponseBody
    @PostMapping("/check-promo")
    private ResponseEntity<?> checkPromoCode(@RequestBody PromoCodeRequest promoCodeRequest) {
        String token = promoCodeRequest.getToken();
        Optional<TrialUser> userOptional = trialUserService.findByPromoCode(token);

        if (userOptional.isPresent()) {
            TrialUser user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>("Промокод не найден", HttpStatus.NOT_FOUND);
        }
    }




    @GetMapping("/{courseId}/module/{moduleId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByCourse(@PathVariable("courseId") long courseId,
                                                          @PathVariable("moduleId") long moduleId) {
        List<QuestionDto> questions = questionService.findAllByModuleID(moduleId);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


//    @PostMapping()
//    public ResponseEntity<?> submitAnswer(@RequestBody Map<Integer, String> userAnswers,
//                                          Authentication authentication) {
//
//        resultHandler.setResults(userAnswers, authentication);
//
//        return new ResponseEntity<>("Ответы приняты успешно", HttpStatus.OK);
//    }
}
