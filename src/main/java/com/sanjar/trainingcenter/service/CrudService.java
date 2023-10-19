package com.sanjar.trainingcenter.service;

import java.util.List;

public interface CrudService<T> {
    void create(T t);
    List<T> findAll();
    T findById(long id);
    void deleteById(long id);
}
