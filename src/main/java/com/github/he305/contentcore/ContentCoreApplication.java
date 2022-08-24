package com.github.he305.contentcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ContentCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentCoreApplication.class, args);
	}

}
