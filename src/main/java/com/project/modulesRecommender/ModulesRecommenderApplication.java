package com.project.modulesRecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ModulesRecommenderApplication {

	public static void main(String[] args) {
//		LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel("org.springframework.data.neo4j", LogLevel.DEBUG);
		SpringApplication.run(ModulesRecommenderApplication.class, args);
	}

}
