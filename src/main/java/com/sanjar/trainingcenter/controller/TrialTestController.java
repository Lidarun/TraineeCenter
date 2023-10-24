package com.sanjar.trainingcenter.controller;

import com.sanjar.trainingcenter.dto.QuestionDto;
import com.sanjar.trainingcenter.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trial-test")
public class TrialTestController {

    private final QuestionService questionService;
//    private final ResultHandler resultHandler;

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
