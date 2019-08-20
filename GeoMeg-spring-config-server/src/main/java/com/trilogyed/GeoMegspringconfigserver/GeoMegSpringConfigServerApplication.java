package com.trilogyed.GeoMegspringconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class GeoMegSpringConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoMegSpringConfigServerApplication.class, args);
	}

}
