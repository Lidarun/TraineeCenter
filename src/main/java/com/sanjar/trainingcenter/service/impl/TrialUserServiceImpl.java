package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.model.TrialUser;
import com.sanjar.trainingcenter.repository.TrialUserRepository;
import com.sanjar.trainingcenter.service.TrialUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrialUserServiceImpl implements TrialUserService {
    private final TrialUserRepository trialUserRepository;

    @Override
    public void create(TrialUser trialUser) {
        trialUserRepository.save(trialUser);
    }

    @Override
    public List<TrialUser> findAll() {
        return trialUserRepository.findAll();
    }

    @Override
    public TrialUser findById(long id) {
        return trialUserRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        trialUserRepository.deleteById(id);
    }
}
