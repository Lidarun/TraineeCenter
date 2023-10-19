package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.dto.QuestionDto;
import com.sanjar.trainingcenter.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService extends CrudService<Question> {
    void update(Question updatedQuestion);
    Page<Question> getItems(Pageable pageable);
    List<QuestionDto> findAllByModuleID(long moduleId);
    List<Question> findAllQuestionsByModuleID(long moduleId);
}
