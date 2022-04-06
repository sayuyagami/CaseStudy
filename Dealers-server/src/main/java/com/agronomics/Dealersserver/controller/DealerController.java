package com.agronomics.Dealersserver.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agronomics.Dealersserver.models.Dealers;
import com.agronomics.Dealersserver.services.DealerService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false"  )
@RequestMapping("/dealers")
public class DealerController {
	
	@Autowired 
	DealerService dealservice;
	
	@GetMapping("/loaduserdata/{user}")
	public UserDetails searchdealer(@PathVariable String user) {
		return dealservice.loadUserByUsername(user);
	}
	
	@GetMapping("/dealersprofile")
	public Dealers currentUserName() {
	     return dealservice.getauthdealerinfo(); 
	}
	
	@PostMapping("/subscribe/{farmerid}")
	public String subscribeid(@PathVariable Long farmerid) {
		Dealers authuser =currentUserName();
		Long dealerid=authuser.getDealerid();
		return dealservice.subscribefarmer(farmerid,dealerid);
	}
	
	@PutMapping(value="/editdet")
	public String updatedealer(@RequestBody Dealers dealersdet) {
		Dealers authuser =currentUserName();
		Long dealerid=authuser.getDealerid();
		return dealservice.updatedealerdet(dealersdet, dealerid);
	}
	
	@DeleteMapping("/deleteid/{dealerid}")
	public String deldet(@PathVariable("dealerid") Long id) {
		return dealservice.dealerdelete(id);
	}
	
	@PostMapping("/unsubscribe/{farmerid}")
	public String unsubscribeid(@PathVariable Long farmerid) {
		Dealers authuser =currentUserName();
		Long dealerid=authuser.getDealerid();
		return dealservice.unsubscribefarmer(farmerid,dealerid);
	}
		
	//get user details
	@GetMapping("/name/{dealername}")
	public Dealers getdetbyname(@PathVariable String dealername) {
		return dealservice.getdealerdetbyname(dealername);
	}

	/******admin operation*************/

	@GetMapping("/admin/dealerslist")
	public List<Dealers> getdealerslist() {
		return dealservice.getdealersdata();
	}

	@GetMapping("/admin/{dealerid}")
	public Optional<Dealers> getdealer(@PathVariable("dealerid") Long id) {
		return dealservice.getdealerdetailsbyid(id);
	}
	@PutMapping(value="/admin/editdet/{did}")
	public String updatedealeradmin(@RequestBody Dealers dealersdet,@PathVariable Long did) {
		
		return dealservice.updatedealerdet(dealersdet, did);
	}
	@DeleteMapping("/admin/deletedealer/{dealerid}")
	public  String deletedealer(@PathVariable("dealerid") Long id) {
		return dealservice.deletedealerbyid(id);
	}
}