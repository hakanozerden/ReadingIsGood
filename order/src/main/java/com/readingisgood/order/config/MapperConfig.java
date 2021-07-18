package com.readingisgood.order.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author hakan.ozerden */
@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper initModelMapper() {
        return new ModelMapper();
    }
}
