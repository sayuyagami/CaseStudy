package com.agronomics.farmersserver.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agronomics.farmersserver.models.Crops;
import com.agronomics.farmersserver.models.Farmers;
import com.agronomics.farmersserver.models.ListCrops;
import com.agronomics.farmersserver.services.FarmerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RequestMapping("/farmers")
@RestController
public class FarmerController {
	@Autowired
	FarmerService farmerservice;
	
	/******Farmer Operations********/	

	@GetMapping("/farmerprofile")
	public Farmers currentUserName() {
	     return farmerservice.getauthfarmerinfo(); 
	}
	
	//get user details
	@GetMapping("/name/{farmernm}")
	public Farmers getdetbyname(@PathVariable("farmernm") String farmernm) {
		return farmerservice.getfarmerdetbyname(farmernm);
	}
	
	//Everyone can access this crop data
	@GetMapping("/cropslist")
	public ListCrops Allcropposts() {
		return farmerservice.Allcrops();
	}
	
	//update status
	@PostMapping("/updatereq/{cropid}/{dealerid}")
	public String updatereqstatus(@PathVariable Long dealerid,@PathVariable Long cropid,@RequestBody String reqstatus) {
		return farmerservice.updatestatusofreq(dealerid,cropid,reqstatus);
	}
		
	@GetMapping("/LoggedInuserposts")
	public ListCrops farmerpostedcrops() {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.displaycropsforcurrentuser(farmerid);
	}
		
	//Farmer posted crop data by name
	@GetMapping("/postsbyname/{cropname}")
	public ListCrops farmercropsbyname(@PathVariable String cropname) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.displaycropsbyname(farmerid,cropname);
	}
	
	//add crops
	@PostMapping(value="/addcrops/{croptype}")
	public String addcroppost(@RequestBody Crops crops,@PathVariable String croptype) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.addcrops(farmerid, crops,croptype);
	}	
	
	/*@PostMapping(value="/addcrops")
	public String addcroppost(@RequestBody Crops crops,@RequestParam("img") MultipartFile file) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.addcrops(farmerid, crops,file);
	}*/	
	
	//edit crops by id
	@PutMapping(value="/cropbyid/type={croptype}/{cropid}")
	public String updatecroppost(@PathVariable Long cropid,@RequestBody Crops crops,@PathVariable String croptype) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.updtcropbyid(farmerid,cropid, crops,croptype);
	}
	
	//update details
	@PutMapping(value="/editdet")
	public String updatefarmer(@RequestBody Farmers farmersdet) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.updatefarmerdet(farmersdet, farmerid);
	}
	
	//edit crops by id
	@PutMapping(value="/cropbyid/type={croptype}/{cropid}/{reqid}")
	public String updatereqstatus(@PathVariable Long cropid,@PathVariable Long reqid,@RequestBody Crops crops,@PathVariable String croptype) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.updtcropreqbyid(farmerid,cropid,reqid, crops,croptype);
	}
	
	
	/**************Admin Operations***************/

	//get user details
	@GetMapping("/id/{farmerid}")
	public Optional<Farmers> getdet(@PathVariable("farmerid") Long id) {
		return farmerservice.getfarmerdet(id);
	}
	
	//search crop data
	@GetMapping("/admin/farmerslist")
	public List<Farmers> Allfarmerposts() {
		return farmerservice.Allfarmers();
	}
}
