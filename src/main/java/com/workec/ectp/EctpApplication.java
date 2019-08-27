package com.workec.ectp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class EctpApplication {

	public static void main(String[] args) {
		SpringApplication.run(EctpApplication.class, args);
	}
}
