package com.agronomics.farmersserver.controller;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.agronomics.farmersserver.models.Farmers;
import com.agronomics.farmersserver.models.Role;
import com.agronomics.farmersserver.models.AuthRequest;
import com.agronomics.farmersserver.models.AuthResponse;

import com.agronomics.farmersserver.repository.FarmerRepository;
import com.agronomics.farmersserver.repository.RoleRepository;
import com.agronomics.farmersserver.services.FarmerService;
import com.agronomics.farmersserver.services.SequenceGeneratorService;
import com.agronomics.farmersserver.util.JwtUtil;

@RestController
public class AuthController {

	@Autowired
	private FarmerRepository farmerRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	AuthenticationManager authmanager;
	
	@Autowired
	JwtUtil jwtutil;
	
	@Autowired
	FarmerService farmerservice;
	
	@Autowired
	PasswordEncoder bcrypt;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@PostMapping("/farmerregister")
	private ResponseEntity<?> registeruser(@RequestBody AuthRequest authenticationRequest) {
	String useremail = authenticationRequest.getFemail();
	String password = authenticationRequest.getFpasswd();
	String username=authenticationRequest.getFarmername();
	Farmers userModel = new Farmers();
	userModel.setFemail(useremail);
	userModel.setFpasswd(bcrypt.encode(password));
	userModel.setFarmername(authenticationRequest.getFarmername());
	userModel.setFphn(authenticationRequest.getFphn());
	Role userRole = roleRepository.findByRole("FARMER");
	userModel.setRoles(new HashSet<>(Arrays.asList(userRole)));
	userModel.setFarmerid(sequenceGeneratorService.generateSequenceforfarmer(Farmers.SEQUENCE_NAME));
	
	try {
		farmerRepository.save(userModel);
		
	} catch (Exception e) {
	return ResponseEntity.ok(new AuthResponse("Error during client Subscription username "+ username ));
	}
	
	return ResponseEntity.ok(new AuthResponse ("Succesfully registered "+username+" as farmer"));
	}

	@PostMapping("/auth")
	private ResponseEntity<String> authenticateuser(@RequestParam(value="useremail") String useremail,
			@RequestParam(value="password") String password) {
		
		try {
			authmanager.authenticate(new UsernamePasswordAuthenticationToken(useremail,password));
			} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized user");
			}
		
		
		String generatedtoken =jwtutil.generateToken(useremail);
		
			return ResponseEntity.ok(generatedtoken);
	}
	
	@GetMapping({ "/hello" })
	 public String hello() {
	 return "Hello JWT";
	 }

}
