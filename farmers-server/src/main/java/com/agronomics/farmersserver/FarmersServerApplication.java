package com.agronomics.farmersserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableHystrix
@EnableEurekaClient
@EnableSwagger2
public class FarmersServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmersServerApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate getresttemplate(){
		return new RestTemplate();
	}
	
	

}
