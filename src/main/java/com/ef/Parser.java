package com.ef;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ef.services.LogService;

@SpringBootApplication
@EnableJpaRepositories
public class Parser implements CommandLineRunner {
	private static final Logger logger = Logger.getLogger(Parser.class.getName());
    @Autowired
    private LogService logService;
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Parser.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
	public void run(String... args) throws Exception {
		logService.getLogs(args);
		
	}
}
