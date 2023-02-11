package com.gdscssu.garbagecollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GarbagecollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GarbagecollectorApplication.class, args);
		System.out.println("Build Success");
	}

}
