package com.agronomics.zuulapigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class JwtClientController {
	
	ResponseEntity<String> authenticationResponse ;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logintoMCs(@RequestParam String username,@RequestParam String password)
	{
		
		String AUTHENTICATION_URL="http://farmer-service/auth";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("username", username);
		map.add("password", password);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		//ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
		RestTemplate restTemplate = new RestTemplate();
		 authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL,
				HttpMethod.POST, request, String.class);
		if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
			{
				//String token = "Bearer " + authenticationResponse.getBody();
				
				return "Login Successfull and Got Token";
			}
			
		return "Invalid credential";
	}
	@RequestMapping("helloClient")
	public String helloClient()
	{
		if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
		{
			String token = "Bearer " + authenticationResponse.getBody();
			String CLIENT_URL="http://farmer-service/farmers/farmerprofile";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
			ResponseEntity<String> helloResponse = restTemplate.exchange(CLIENT_URL, HttpMethod.GET, jwtEntity,
					String.class);
			if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
				String data = helloResponse.getBody();
				return data;
			}
		}
		return "Please login";
	}
	
}
