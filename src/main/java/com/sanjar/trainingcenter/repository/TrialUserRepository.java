package com.sanjar.trainingcenter.repository;

import com.sanjar.trainingcenter.model.TrialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrialUserRepository extends JpaRepository<TrialUser, Long> {
}
