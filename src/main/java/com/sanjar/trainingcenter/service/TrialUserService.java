package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.model.TrialUser;

import java.util.Optional;

public interface TrialUserService extends CrudService<TrialUser> {
    Optional<TrialUser> findByPromoCode(String promoCode);
}
