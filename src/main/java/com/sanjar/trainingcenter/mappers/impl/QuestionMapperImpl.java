package com.sanjar.trainingcenter.mappers.impl;

import com.sanjar.trainingcenter.dto.QuestionDto;
import com.sanjar.trainingcenter.mappers.EntityMapper;
import com.sanjar.trainingcenter.model.Question;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapperImpl implements EntityMapper<Question, QuestionDto> {

    @Override
    public QuestionDto map(Question question) {
        QuestionDto dto = new QuestionDto();

        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setCorrectAnswer(question.getOptions().get(question.getCorrectAnswer()));

        question.getOptions().remove((int) question.getCorrectAnswer());
        dto.setOptions(question.getOptions()) ;

        return dto;
    }

}