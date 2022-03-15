package com.agronomics.Dealersserver.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agronomics.Dealersserver.models.Crops;
import com.agronomics.Dealersserver.models.Dealers;
import com.agronomics.Dealersserver.services.DealerService;


@RestController
@RequestMapping("/dealers")
public class DealerController {
	
	@Autowired 
	DealerService dealservice;
	
	@GetMapping("/admin/dealerslist")
	public List<Dealers> getdealerslist() {
		return dealservice.getdealersdata();
	}
	
	
	/*@GetMapping("/{dealerid}/viewpurchases")
	public Stream<Purchases> getdealerprchases(@PathVariable Long dealerid) {
		return dealservice.getpurchasesbydealerid(dealerid);
	}*/
	

	@GetMapping("/{dealerid}")
	public Optional<Dealers> getdealer(@PathVariable("dealerid") Long id) {
		return dealservice.getdealerdetailsbyid(id);
	}
	
	//get user details
	@GetMapping("/name/{dealername}")
	public Dealers getdetbyname(@PathVariable String dealername) {
		return dealservice.getdealerdetbyname(dealername);
	}
			
	//dealer making purchase
	@PostMapping("/{dealerid}/kharifcroppurchase/{cropid}")
	public String makekharifpurchase(@PathVariable("dealerid") Long id,@RequestBody Crops reqcrops,@PathVariable Long cropid) {
		return dealservice.purchaseforKharifcrop(id, reqcrops, cropid);
	}
	
	//dealer making purchase
	@PostMapping("/{dealerid}/Rabbicroppurchase/{cropid}")
	public String makeRabbipurchase(@PathVariable("dealerid") Long id,@RequestBody Crops reqcrops,@PathVariable Long cropid) {
		return dealservice.purchaseforRabbicrop(id, reqcrops, cropid);
	}
}