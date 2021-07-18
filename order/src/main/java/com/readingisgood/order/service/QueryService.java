package com.readingisgood.order.service;

import java.util.Collection;
import java.util.Optional;

/**
 * @author hakan.ozerden
 * @param <T>
 */
public interface QueryService<T> {
    Optional<T> findById(String id);

    Collection<T> findAll();
}
