package com.tingeso.msrepairprices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsRepairPricesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRepairPricesApplication.class, args);
	}

}
