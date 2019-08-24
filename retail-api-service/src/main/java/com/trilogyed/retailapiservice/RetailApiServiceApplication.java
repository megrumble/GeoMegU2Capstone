package com.trilogyed.retailapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RetailApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailApiServiceApplication.class, args);
	}

}
