package com.cos.photogramstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/application-aws.yml")
public class PhotogramStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotogramStartApplication.class, args);
	}
}
