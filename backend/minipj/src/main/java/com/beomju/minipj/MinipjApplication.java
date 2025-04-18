package com.beomju.minipj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MinipjApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinipjApplication.class, args);
	}

}
