package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.model.Medal;
import com.sanjar.trainingcenter.repository.MedalRepository;
import com.sanjar.trainingcenter.service.MedalService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedalServiceImpl implements MedalService {

    private final MedalRepository medalRepository;

    @Override
    public void create(Medal medal) {
        medalRepository.save(medal);
    }

    @Override
    @Cacheable(cacheNames = "medals")
    public List<Medal> findAll() {
        return medalRepository.findAll();
    }


    @Override
    @CachePut(cacheNames = "medals")
    public List<Medal> updateCache() {
        return medalRepository.findAll();
    }

    @Override
    public void update(long id, Medal medal) {
        Optional<Medal> medalDB = medalRepository.findById((long) medal.getId());

        if (medalDB.isPresent())
            medalRepository.save(medal);
    }

    @Override
    public Medal findById(long id) {
        return medalRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        medalRepository.deleteById(id);
    }
}
