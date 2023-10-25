package com.sanjar.trainingcenter.service;

import com.sanjar.trainingcenter.model.Medal;

import java.util.List;

public interface MedalService extends CrudService<Medal> {
    List<Medal> updateCache();
    void update(long id, Medal medal);
}
