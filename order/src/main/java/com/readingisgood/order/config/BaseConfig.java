package com.readingisgood.order.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/** @author hakan.ozerden */
@Configuration
@ComponentScan(basePackages = {"com.readingisgooding.order"})
@EntityScan(basePackages = {"com.readingisgooding.order.domain.entity"})
@EnableMongoAuditing
public class BaseConfig {}
