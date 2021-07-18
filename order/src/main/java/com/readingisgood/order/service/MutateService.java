package com.readingisgood.order.service;

/**
 * @author hakan.ozerden
 * @param <E>
 */
public interface MutateService<E> {
    E mutate(E current, E changes);
}
