package org.lemur.lemurmall.lemurthirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@ConfigurationPropertiesScan
public class LemurThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemurThirdPartyApplication.class, args);
    }

}
