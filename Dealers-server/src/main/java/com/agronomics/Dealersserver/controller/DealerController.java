package com.agronomics.Dealersserver.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agronomics.Dealersserver.models.Crops;
import com.agronomics.Dealersserver.models.Cropsdata;
import com.agronomics.Dealersserver.models.Dealers;
import com.agronomics.Dealersserver.models.ListCrops;
import com.agronomics.Dealersserver.services.DealerService;

@RestController
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
	
	//Everyone can access this crop data
	@GetMapping("/cropslist")
	public ListCrops Allcropposts() {
		return dealservice.Allcrops();
	}
	
	@PostMapping("/subscribe/{farmerid}")
	public String subscribeid(@PathVariable Long farmerid) {
		Dealers authuser =currentUserName();
		Long dealerid=authuser.getDealerid();
		return dealservice.subscribefarmer(farmerid,dealerid);
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
			
//dealer making purchase
	@PostMapping("/croppurchase/{cropid}")
	public String makekharifpurchase(@RequestBody Crops reqcrops,@PathVariable Long cropid) {
		Dealers authuser =currentUserName();
		Long dealerid=authuser.getDealerid();
		return dealservice.purchaseforKharifcrop(dealerid, reqcrops, cropid);
	}
	
	@PostMapping("/pay/{cropid}")
	public String makekpayment(@PathVariable Long cropid) {
		Dealers authuser =currentUserName();
		String dealername=authuser.getDealername();
		return dealservice.payforfarmer(dealername,cropid);		
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
}