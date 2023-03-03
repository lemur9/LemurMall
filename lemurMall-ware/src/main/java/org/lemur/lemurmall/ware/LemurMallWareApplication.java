package org.lemur.lemurmall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LemurMallWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(LemurMallWareApplication.class, args);
	}

}
