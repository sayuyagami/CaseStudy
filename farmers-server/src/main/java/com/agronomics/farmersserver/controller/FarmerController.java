package com.agronomics.farmersserver.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agronomics.farmersserver.models.CropImages;
import com.agronomics.farmersserver.models.Crops;
import com.agronomics.farmersserver.models.Cropsdata;
import com.agronomics.farmersserver.models.Farmers;
import com.agronomics.farmersserver.models.ListCrops;
import com.agronomics.farmersserver.services.FarmerService;
import java.util.Base64;
import io.swagger.models.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RequestMapping("/farmers")
@RestController
public class FarmerController {
	@Autowired
	FarmerService farmerservice;
	/******Farmer Operations
	 * @return ********/	
	@GetMapping("/loaduserdata/{user}")
	public UserDetails searchfarmer(@PathVariable String user) {
		return farmerservice.loadUserByUsername(user);
	}

	@GetMapping("/farmerprofile")
	public Farmers currentUserName() {
	     return farmerservice.getauthfarmerinfo(); 
	}
	
	//get user details
	@GetMapping("/name/{farmernm}")
	public Farmers getdetbyname(@PathVariable("farmernm") String farmernm) {
		return farmerservice.getfarmerdetbyname(farmernm);
	}
	
	@GetMapping("/croptypes")
	public List<String> Allcroptypes() throws Exception {
		return farmerservice.Allcroptypes();
	}
	
	
	//Everyone can access this crop data
	@GetMapping("/cropslist")
	public List<Cropsdata> Allcropposts() {
		return farmerservice.Allcrops();
	}
	
	//update status
	/*@PostMapping("/updatereq/{cropid}/{dealerid}")
	public String updatereqstatus(@PathVariable Long dealerid,@PathVariable Long cropid,@RequestBody String reqstatus) {
		return farmerservice.updatestatusofreq(dealerid,cropid,reqstatus);
	}*/
		
	@GetMapping("/LoggedInuserposts")
	public  List<Cropsdata> farmerpostedcrops() {
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
	/*@PostMapping(value="/addcrops/{croptype}")
	public String addcroppost(@RequestBody Crops crops,
			@PathVariable String croptype) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.addcrops(farmerid, crops,croptype);
	}*/
	
	//add image
	@Operation(  // Swagger/OpenAPI 3.x annotation to describe the endpoint
		    summary = "Small summary of the end-point",
		    description = "A detailed description of the end-point"
		) 
		@PostMapping(value = "/addcropimage/{cropid}", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})  
	public String addcroppost(@PathVariable String cropid,
			@Parameter(
			        description = "Files to be uploaded", 
			        content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)  // Won't work without OCTET_STREAM as the mediaType.
			    )
	@RequestPart("file") MultipartFile file) throws IOException {
		
		return farmerservice.addcropimage(cropid,file);
	}	
	
	//getImage
	@GetMapping("/photos/{id}")
	public String getPhoto(@PathVariable String id) {
		CropImages photo = farmerservice.getPhoto(id);
	    //new CropImages(photo.getImgid(),photo.getImgtitle(),Base64.getEncoder().encodeToString(photo.getImage().getData()))
	    return Base64.getEncoder().encodeToString(photo.getImage().getData());
	}
	
	//edit crops by id
	/*@PutMapping(value="/cropbyid/{cropid}")
	public String updatecroppost(@PathVariable Long cropid,@RequestBody Crops crops,@PathVariable String croptype) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.updtcropbyid(farmerid,cropid, crops,croptype);
	}*/
	
	//update details
	@PutMapping(value="/editdet")
	public String updatefarmer(@RequestBody Farmers farmersdet) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.updatefarmerdet(farmersdet, farmerid);
	}
	
	//edit crops by id
	/*@PutMapping(value="/cropbyid/{cropid}/{reqid}")
	public String updatereqstatus(@PathVariable Long cropid,@PathVariable Long reqid,@RequestBody Crops crops,@PathVariable String croptype) {
		Farmers authuser =currentUserName();
		Long farmerid=authuser.getFarmerid();
		return farmerservice.updtcropreqbyid(farmerid,cropid,reqid, crops,croptype);
	}*/
	
	
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
