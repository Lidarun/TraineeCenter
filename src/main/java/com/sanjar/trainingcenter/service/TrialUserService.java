package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.dto.TrialUserRequest;
import com.sanjar.trainingcenter.model.TrialUser;

import java.util.List;
import java.util.Optional;

public interface TrialUserService extends CrudService<TrialUser> {
    Optional<TrialUser> findByPromoCode(String promoCode);
    void setResult(TrialUserRequest user);
    void update(long id, TrialUser trialUser);
    List<TrialUser> updateCache();
    List<TrialUser> getTopStudentsByResult(int count);
}
