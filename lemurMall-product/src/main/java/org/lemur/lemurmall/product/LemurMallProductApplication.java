package org.lemur.lemurmall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LemurMallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(LemurMallProductApplication.class, args);
	}

}
