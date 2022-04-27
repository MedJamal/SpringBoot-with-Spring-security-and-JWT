package com.medjamal.ouazani.springsecuritydemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringsecuritydemoApplication implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(SpringsecuritydemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringsecuritydemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Application started successfully");
	}
}
