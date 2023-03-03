package org.lemur.lemurmall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "org.lemur.lemurmall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class LemurMallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemurMallMemberApplication.class, args);
    }

}
