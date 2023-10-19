package com.sanjar.trainingcenter.repository;

import com.sanjar.trainingcenter.model.Module;
import com.sanjar.trainingcenter.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAll(Pageable pageable);
    List<Question> findAllByModule(Module module);
}
