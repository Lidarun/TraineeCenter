package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.model.Question;
import org.springframework.validation.ObjectError;

public interface QuestionValidationService {
    ObjectError checkFields(Question question);
}