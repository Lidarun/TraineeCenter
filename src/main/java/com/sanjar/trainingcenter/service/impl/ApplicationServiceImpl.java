package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.model.Application;
import com.sanjar.trainingcenter.repository.ApplicationRepository;
import com.sanjar.trainingcenter.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    public Application create(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public void deleteById(long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    @Cacheable("applications")
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    @CachePut("applications")
    public List<Application> updateCache() {
        return applicationRepository.findAll();
    }

}
