package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.model.Application;

import java.util.List;

public interface ApplicationService {
    Application create(Application application);
    void deleteById(long id);
    List<Application> findAll();
}
