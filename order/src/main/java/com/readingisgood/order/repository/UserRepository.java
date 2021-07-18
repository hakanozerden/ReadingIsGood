package com.readingisgood.order.repository;

import com.readingisgood.order.domain.entity.User;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/** @author hakan.ozerden */
@JaversSpringDataAuditable
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
}
