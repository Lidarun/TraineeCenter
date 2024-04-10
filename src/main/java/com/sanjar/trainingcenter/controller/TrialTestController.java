package com.sanjar.trainingcenter.controller;

import com.sanjar.trainingcenter.dto.PromoCodeRequest;
import com.sanjar.trainingcenter.dto.QuestionDto;
import com.sanjar.trainingcenter.dto.TrialUserRequest;
import com.sanjar.trainingcenter.model.TrialUser;
import com.sanjar.trainingcenter.service.QuestionService;
import com.sanjar.trainingcenter.service.TrialUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trial-test")
public class TrialTestController {

    private final QuestionService questionService;
    private final TrialUserService trialUserService;

    @GetMapping
    private String showPage() {
        return "trial-test";
    }

    @GetMapping("/ort")
    public ResponseEntity<List<QuestionDto>> getTrialQuestions() {
        List<QuestionDto> questions = questionService.findAllByModuleID(1);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/ort")
    public ResponseEntity<?> getTrialTestResults(@RequestBody TrialUserRequest user) {
        trialUserService.setResult(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

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

}
