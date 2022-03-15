package com.javatechie.spring.paytm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringPaymentPaytmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPaymentPaytmApplication.class, args);
	}

}
