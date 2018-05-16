package com.t2.keepfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.t2")
public class KeepFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeepFileApplication.class, args);
	}
}
