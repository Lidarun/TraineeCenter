package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.model.Course;
import com.sanjar.trainingcenter.model.Module;
import com.sanjar.trainingcenter.model.Question;
import com.sanjar.trainingcenter.repository.ModuleRepository;
import com.sanjar.trainingcenter.service.CourseService;
import com.sanjar.trainingcenter.service.ModuleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRep;
    private final CourseService courseService;


    @Override
    @Transactional
    public void create(Module module) {
        Course course = module.getCourse();
        Module moduleDB = moduleRep.save(module);

        course.addModule(moduleDB);
        courseService.update(course.getId(), course);
    }

    @Override
    @Cacheable("modules")
    public List<Module> findAll() {
        return moduleRep.findAll();
    }

    @Override
    @CachePut("modules")
    public List<Module> updateCache() {
        return moduleRep.findAll();
    }

    @Override
    public void deleteById(long id) {
        moduleRep.deleteById(id);
    }

    @Override
    public Module findById(long id) {
        Optional<Module> module = moduleRep.findById(id);

        return module.orElse(null);

    }

    @Override
    public void update(long id, Module updatedModule) {
        if (moduleRep.findById(id).isPresent())
            moduleRep.save(updatedModule);
    }

    @Override
    public List<Module> getModulesByCourseId(Long courseId) {
        return moduleRep.findAllByCourse(courseService.findById(courseId));
    }

    @Override
    public Module findByQuestion(Question question) {
        return moduleRep.findByQuestions(question);
    }
}