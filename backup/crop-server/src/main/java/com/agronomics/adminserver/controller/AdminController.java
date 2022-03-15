package com.agronomics.adminserver.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.agronomics.adminserver.models.KharifCrops;
import com.agronomics.adminserver.models.ListCrops;
import com.agronomics.adminserver.models.RabbiCrops;
import com.agronomics.adminserver.service.AdminServices;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminServices adminservices;
		
	@RequestMapping("/dashboard")
	public String getadmindetails() {
	//	return adminservices.getdetails(adminname);
		return "Welcome Admin";
	}
	
	@GetMapping("/cropslist")
	public ListCrops Allcropposts() throws Exception {
		return adminservices.Allcrops();
	}
	
	@GetMapping("/cropslist/Kharif/{cropid}")
	public Optional<KharifCrops> Kharifcroppostsbyid(@PathVariable Long cropid) throws Exception {
		return adminservices.Kharifcropsbyid(cropid);
	}
	
	@GetMapping("/cropslist/Rabbi/{cropid}")
	public Optional<RabbiCrops> Rabbicroppostsbyid(@PathVariable Long cropid) throws Exception {
		return adminservices.rabbicropsbyid(cropid);
	}
	
	@GetMapping("/cropslist/{farmerid}")
	public ListCrops Allfarmerposts(@PathVariable long farmerid) throws Exception {
		return adminservices.Allcropsbyfarmerid(farmerid);
	}
	
	@GetMapping("/Kharifcropslist")
	public List<KharifCrops> Kharifcropposts() throws Exception {
		return adminservices.AllKharifcrops();
	}
	
	@GetMapping("/Rabbicropslist")
	public List<RabbiCrops> Rabbicropposts() throws Exception {
		return adminservices.AllRabbicrops();
	}
	/*@GetMapping("/viewposts")
	public List<KharifCrops> getfarmerposts() {
		
	}*/
}