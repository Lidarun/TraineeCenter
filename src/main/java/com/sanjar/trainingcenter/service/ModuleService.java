package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.model.Module;
import com.sanjar.trainingcenter.model.Question;

import java.util.List;

public interface ModuleService extends CrudService<Module> {
    void update(long id, Module updatedModule);
    List<Module> getModulesByCourseId(Long courseId);
    List<Module> updateCache();
    Module findByQuestion(Question question);
}
