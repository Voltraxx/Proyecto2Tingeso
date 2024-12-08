package com.tingeso.mscars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCarsApplication.class, args);
	}

}
