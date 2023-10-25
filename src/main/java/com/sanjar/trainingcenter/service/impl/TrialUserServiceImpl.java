package com.sanjar.trainingcenter.service.impl;

import com.sanjar.trainingcenter.dto.TrialUserRequest;
import com.sanjar.trainingcenter.model.Medal;
import com.sanjar.trainingcenter.model.TrialUser;
import com.sanjar.trainingcenter.repository.MedalRepository;
import com.sanjar.trainingcenter.repository.TrialUserRepository;
import com.sanjar.trainingcenter.service.TrialUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrialUserServiceImpl implements TrialUserService {
    private final TrialUserRepository trialUserRepository;
    private final MedalRepository medalRepository;

    @Override
    public void create(TrialUser trialUser) {
        trialUserRepository.save(trialUser);
    }

    @Override
    @Cacheable(cacheNames = "trial-users")
    public List<TrialUser> findAll() {
        return trialUserRepository.findAll();
    }

    @Override
    @CachePut(cacheNames = "trial-users")
    public List<TrialUser> updateCache() {
        return trialUserRepository.findAll();
    }

    @Override
    public List<TrialUser> getTopStudentsByResult(int count) {
        List<TrialUser> trialUsers = trialUserRepository.findAll();
        return trialUsers.stream()
                .filter(user -> user.getResult() > 0)
                .sorted(Comparator.comparingInt(TrialUser::getResult).reversed())
                .limit(10)
                .toList();
    }

    @Override
    public TrialUser findById(long id) {
        return trialUserRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        trialUserRepository.deleteById(id);
    }

    @Override
    public Optional<TrialUser> findByPromoCode(String promoCode) {
        return trialUserRepository.findByTokenAndResultEquals(promoCode,  0);
    }

    @Override
    public void setResult(TrialUserRequest user) {
        Optional<TrialUser> trialUser = trialUserRepository.findById(user.getId());

        if (trialUser.isPresent()) {
            List<Medal> medals = medalRepository.findAll();
            int result = user.getResult();

            medals.sort(Comparator.comparing(Medal::getScore).reversed());

            String medal =  medals.stream()
                    .filter(m -> result >= m.getScore())
                    .map(Medal::getMedalUrl)
                    .findFirst()
                    .orElse("https://wallpaperaccess.com/full/1556608.jpg");


            trialUser.get().setMedalImage(medal);
            trialUser.get().setResult(user.getResult());
            trialUserRepository.save(trialUser.get());
        }
    }

    @Override
    public void update(long id, TrialUser trialUser) {
        Optional<TrialUser> trialUserDB = trialUserRepository.findById(id);

        if (trialUserDB.isPresent())
            trialUserRepository.save(trialUser);
    }
}
