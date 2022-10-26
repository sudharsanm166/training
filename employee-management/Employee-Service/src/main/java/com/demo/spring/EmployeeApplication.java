package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}
	
	@Bean
    public OpenAPI openApiDoc() {
        return new OpenAPI().info(new Info()
                .description("Mock Project on Employee-management")
                .license(new License().name("Free for All"))
                .title("Mock project Document")
                .version("1.0.1-BETA"));
    }

}
