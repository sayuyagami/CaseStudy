package com.agronomics.zuulapigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.agronomics.zuulapigateway.model.AuthRequest;
import com.agronomics.zuulapigateway.model.AuthResponse;
import com.agronomics.zuulapigateway.model.Crops;
import com.agronomics.zuulapigateway.model.Farmers;
import com.agronomics.zuulapigateway.model.ReceiveMessage;
import com.agronomics.zuulapigateway.model.Token;
import com.agronomics.zuulapigateway.util.JwtUtil;


@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false"  ) 
public class AuthController {

	@Autowired
	JwtUtil jwtutil;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@PostMapping("/farmerauth")
	private ResponseEntity<?> authenticateuser(@RequestBody AuthRequest req) {
		String generatedtoken =jwtutil.generateToken(req.getUseremail());
		String token = "Bearer " + generatedtoken;
		String CLIENT_URL="http://localhost:8082/farmers/loaduserdata/"+req.getUseremail();
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		
		try {
		ResponseEntity<String> helloResponse = restTemplate.exchange(CLIENT_URL, HttpMethod.GET, jwtEntity,
					String.class);
		if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
		String data = helloResponse.getBody();
		System.out.println(data);
		
		return ResponseEntity.ok(new AuthResponse(generatedtoken));
		}
		}catch (Exception e) {
			return ResponseEntity.ok(new AuthResponse("Not an Authorized user" ));
		}
			
		return ResponseEntity.ok(new AuthResponse("Please Check your Credentials"));
			
	}
	
	@PostMapping("/agrofarmers")
	private ResponseEntity<?> authenticateuserdetails(@RequestBody Token tk) {
		
		String token = "Bearer " + tk.getToken();
		String CLIENT_URL="http://localhost:8082/farmers/"+tk.getPassedUrl();
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		
		try {
		ResponseEntity<String> helloResponse = restTemplate.exchange(CLIENT_URL, HttpMethod.GET, jwtEntity,
					String.class);
		if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
		String data = helloResponse.getBody();
		System.out.println(data);
		
		return ResponseEntity.ok(data);
		}
		}catch (Exception e) {
			return ResponseEntity.ok(new AuthResponse("Not an Authorized user" ));
		}
			
		return ResponseEntity.ok(new AuthResponse("Please Check your Credentials"));
			
	}
	
	@PostMapping("/agroupdate/{tk}/{fetchurl}")
	private ResponseEntity<?> updatedetails(@PathVariable String tk,@PathVariable String fetchurl,@RequestBody Farmers fm) {
		String CLIENT_URL;
		String token = "Bearer " + tk;
		if(fetchurl=="dealereditdet") {
			CLIENT_URL="http://localhost:8083/dealers/"+fetchurl;
		}else {
		CLIENT_URL="http://localhost:8082/farmers/"+fetchurl;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		HttpEntity<Farmers> requestUpdate = new HttpEntity<>(fm, headers);
		
		try {
		ResponseEntity<String> helloResponse = restTemplate.exchange(CLIENT_URL, HttpMethod.PUT, requestUpdate,
					String.class);
		if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
		String data = helloResponse.getBody();
		System.out.println(data);
		
		return ResponseEntity.ok(data);
		}
		}catch (Exception e) {
			return ResponseEntity.ok(new AuthResponse("Not an Authorized user" ));
		}
			
		return ResponseEntity.ok(new AuthResponse("Please Check your Credentials"));
			
	}
	
	@PostMapping("/agroadd/{tk}")
	private ResponseEntity<?> addnewdata(@PathVariable String tk,@RequestBody Crops crp) {
		
		String token = "Bearer " + tk;
		String CLIENT_URL="http://localhost:8082/farmers/addcrops/"+crp.getCroptype();
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		HttpEntity<Crops> requestUpdate = new HttpEntity<>(crp, headers);
		
		try {
		ResponseEntity<String> helloResponse = restTemplate.exchange(CLIENT_URL, HttpMethod.POST, requestUpdate,
					String.class);
		if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
		String data = helloResponse.getBody();
		System.out.println(data);
		
		return ResponseEntity.ok(data);
		}
		}catch (Exception e) {
			return ResponseEntity.ok(new AuthResponse("Not an Authorized user" ));
		}
			
		return ResponseEntity.ok(new AuthResponse("Please Check your Credentials"));
			
	}
	
	
	@PostMapping("/dealerauth")
	private ResponseEntity<?> authdealer(@RequestBody AuthRequest req) {
		String generatedtoken =jwtutil.generateToken(req.getUseremail());
		String token = "Bearer " + generatedtoken;
		String CLIENT_URL="http://localhost:8083/dealers/loaduserdata/"+req.getUseremail();
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		
		try {
		ResponseEntity<String> helloResponse = restTemplate.exchange(CLIENT_URL, HttpMethod.GET, jwtEntity,
					String.class);
		if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
		String data = helloResponse.getBody();
		System.out.println(data);
		
		return ResponseEntity.ok(new AuthResponse(generatedtoken));
		}
		}catch (Exception e) {
			return ResponseEntity.ok(new AuthResponse("Not an Authorized user" ));
		}
			
		return ResponseEntity.ok(new AuthResponse("Please Check your Credentials"));
			
	}

	@PostMapping("/agrodealers")
	private ResponseEntity<?> authenticatedealerdetails(@RequestBody Token tk) {
		
		String token = "Bearer " + tk.getToken();
		String CLIENT_URL="http://localhost:8083/dealers/"+tk.getPassedUrl();
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		
		try {
		ResponseEntity<String> helloResponse = restTemplate.exchange(CLIENT_URL, HttpMethod.GET, jwtEntity,
					String.class);
		if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
		String data = helloResponse.getBody();
		System.out.println(data);
		
		return ResponseEntity.ok(data);
		}
		}catch (Exception e) {
			return ResponseEntity.ok(new AuthResponse("Not an Authorized user" ));
		}
			
		return ResponseEntity.ok(new AuthResponse("Please Check your Credentials"));
			
	}
	
	@PostMapping("/agrodealersubs/{fid}")
	private ResponseEntity<?> subdealerdetails(@RequestBody Token tk,@PathVariable Long fid) {
		
		String token = "Bearer " + tk.getToken();
		String CLIENT_URL="http://localhost:8083/dealers/"+tk.getPassedUrl()+"/"+fid;
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		
		try {
		ResponseEntity<String> helloResponse = restTemplate.exchange(CLIENT_URL, HttpMethod.POST, jwtEntity,
					String.class);
		if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
		String data = helloResponse.getBody();
		System.out.println(data);
		
		return ResponseEntity.ok(data);
		}
		}catch (Exception e) {
			return ResponseEntity.ok(new AuthResponse("Not an Authorized user" ));
		}
			
		return ResponseEntity.ok(new AuthResponse("Please Check your Credentials"));
			
	}
	
	@GetMapping({ "/hello" })
	 public String hello() {
	 return "Hello JWT";
	 }
}
