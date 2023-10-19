package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.dto.QuestionDto;
import com.sanjar.trainingcenter.mappers.EntityMapper;
import com.sanjar.trainingcenter.model.Module;
import com.sanjar.trainingcenter.model.Question;
import com.sanjar.trainingcenter.repository.QuestionRepository;
import com.sanjar.trainingcenter.service.ModuleService;
import com.sanjar.trainingcenter.service.QuestionService;
import com.sanjar.trainingcenter.service.QuestionValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService, QuestionValidationService {

    private final QuestionRepository questionRep;
    private final ModuleService moduleService;
    private final EntityMapper<Question, QuestionDto> mapper;

    @Override
    public void create(Question question) {
        questionRep.save(question);
    }

    @Override
    public List<Question> findAll() {
        return questionRep.findAll();
    }

    @Override
    public Question findById(long id) {
        return questionRep.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        questionRep.deleteById(id);
    }

    @Override
    public Page<Question> getItems(Pageable pageable) {
        return questionRep.findAll(pageable);
    }

    @Override
    public List<QuestionDto> findAllByModuleID(long moduleId) {
        Module module = moduleService.findById(moduleId);
        List<Question> questions = questionRep.findAllByModule(module);

        return questions.stream()
                .map(q -> {
                    QuestionDto dto;
                    dto = mapper.map(q);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Question> findAllQuestionsByModuleID(long moduleId) {
        Module module = moduleService.findById(moduleId);
        return questionRep.findAllByModule(module);
    }

    @Override
    public void update(Question updatedQuestion) {
        if (questionRep.existsById(updatedQuestion.getId()))
            questionRep.saveAndFlush(updatedQuestion);
    }

    @Override
    public ObjectError checkFields(Question question) {
        ObjectError error = null;
        Integer correctAnswer = question.getCorrectAnswer();

        if (correctAnswer == null) error =
                new ObjectError("global", "Укажите правильный вариант");

        return error;
    }
}
