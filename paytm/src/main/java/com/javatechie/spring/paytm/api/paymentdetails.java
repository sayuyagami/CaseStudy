package com.javatechie.spring.paytm.api;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class paymentdetails {
	private String custid;
	private String custamt;
	private String cropid;

}
