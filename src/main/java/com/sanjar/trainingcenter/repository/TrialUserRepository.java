package com.sanjar.trainingcenter.repository;

import com.sanjar.trainingcenter.model.TrialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrialUserRepository extends JpaRepository<TrialUser, Long> {
    Optional<TrialUser> findByToken(String token);
}
