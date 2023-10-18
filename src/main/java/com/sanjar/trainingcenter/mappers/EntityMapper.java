package com.sanjar.trainingcenter.mappers;

public interface EntityMapper<From, To> {
    To map(From entity);
}
