package com.readingisgood.order;

import com.readingisgood.order.config.BaseConfig;
import com.readingisgood.order.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({SwaggerConfig.class, BaseConfig.class})
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
