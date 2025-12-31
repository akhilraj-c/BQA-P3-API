package com.mindteck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
public class MindteckApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(MindteckApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
