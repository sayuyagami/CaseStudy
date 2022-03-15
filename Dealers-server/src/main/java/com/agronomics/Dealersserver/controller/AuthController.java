package com.agronomics.Dealersserver.controller;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.agronomics.Dealersserver.models.AuthRequest;
import com.agronomics.Dealersserver.models.AuthResponse;
import com.agronomics.Dealersserver.models.Dealers;
import com.agronomics.Dealersserver.models.Role;
import com.agronomics.Dealersserver.repository.DealersRepository;
import com.agronomics.Dealersserver.repository.RoleRepository;
import com.agronomics.Dealersserver.services.SequenceGeneratorService;
import com.agronomics.Dealersserver.util.JwtUtil;

@RestController
public class AuthController {

	@Autowired
	private DealersRepository dealerRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	AuthenticationManager authmanager;
	
	@Autowired
	JwtUtil jwtutil;
	
	@Autowired
	PasswordEncoder bcrypt;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@PostMapping("/dealerregister")
	private ResponseEntity<?> registerdealeruser(@RequestBody AuthRequest authenticationRequest) {
	String useremail = authenticationRequest.getDemail();
	String password = authenticationRequest.getDpasswd();
	String username=authenticationRequest.getDealername();
	Dealers userModel = new Dealers();
	userModel.setDemail(useremail);
	userModel.setDpasswd(bcrypt.encode(password));
	userModel.setDealername(authenticationRequest.getDealername());
	userModel.setDphn(authenticationRequest.getDphn());
	Role userRole = roleRepository.findByRole("DEALER");
	userModel.setRoles(new HashSet<>(Arrays.asList(userRole)));
	userModel.setDealerid(sequenceGeneratorService.generateSequencefordealers(Dealers.SEQUENCE_NAME));
	
	try {
		dealerRepository.save(userModel);
	} catch (Exception e) {
	return ResponseEntity.ok(new AuthResponse("Error during client Subscription username "+ username ));
	}
	return ResponseEntity.ok(new AuthResponse ("Succesfully registered "+username+" as dealer"));
	}
	
	
	@GetMapping("/welcomprofile")
	public String currentUserName() {
	     return "Hi dealer"; 
	}

	@PostMapping("/auth")
	private ResponseEntity<?> authenticateuser(@RequestParam(value="useremail") String useremail,
			@RequestParam(value="password") String password) {
		
		try {
			authmanager.authenticate(new UsernamePasswordAuthenticationToken(useremail,password));
			} catch (Exception e) {
			return ResponseEntity.ok(new AuthResponse("Error during client authentication" ));
			}
		
		
		String generatedtoken =jwtutil.generateToken(useremail);
		
			return ResponseEntity.ok(new AuthResponse(generatedtoken));
	}
}
