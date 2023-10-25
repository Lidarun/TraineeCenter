package com.sanjar.trainingcenter.repository;

import com.sanjar.trainingcenter.model.Medal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedalRepository extends JpaRepository<Medal, Long> {
}
