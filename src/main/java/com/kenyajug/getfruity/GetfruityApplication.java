package com.kenyajug.getfruity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class GetfruityApplication {
	public static void main(String[] args) {
		SpringApplication.run(GetfruityApplication.class, args);
	}
}
