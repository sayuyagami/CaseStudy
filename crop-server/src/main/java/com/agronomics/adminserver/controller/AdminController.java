package com.agronomics.adminserver.controller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.agronomics.adminserver.models.CropImages;
import com.agronomics.adminserver.models.Crops;
import com.agronomics.adminserver.models.Cropsdata;
import com.agronomics.adminserver.models.Farmers;
import com.agronomics.adminserver.models.payModel;
import com.agronomics.adminserver.service.AdminServices;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false"  )
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminServices adminservices;
		
	@GetMapping("/dashboard")
	public String getadmindetails() {
	//	return adminservices.getdetails(adminname);
		return "Welcome Admin";
	}
	
	@GetMapping("/croptypes")
	public List<String> Allcroptypes() throws Exception {
		return adminservices.Allcropstypes();
	}
	
	@GetMapping("/farmerslist")
	public List<Farmers> Allfarmers(){
		return adminservices.Allauthfarmers();
	}
	
	@GetMapping("/cropslist")
	public ResponseEntity<?> Allcropposts() throws Exception {
		return adminservices.Allcrops();
	}
	
	@GetMapping("/dealersubscribedlist/{dealerid}")
	public ResponseEntity<?> subscribedcropposts(@PathVariable Long dealerid) throws Exception {
		return adminservices.subscribedposts(dealerid);
	}
	
	@GetMapping("/cropname/{crp}")
	public ResponseEntity<?> croppostsbyid(@PathVariable String crp) throws Exception {
		return adminservices.cropsbyname(crp);
	}
	
	@GetMapping("/cropslist/farmerid={farmerid}")
	public List<Cropsdata> Allfarmerposts(@PathVariable long farmerid) throws Exception {
		return adminservices.Allcropsbyfarmerid(farmerid);
	}
	
	@PostMapping("/paydata")
	public String payments(@RequestBody payModel pay) {
		return adminservices.paymentbydealer(pay);
	}
	
	@GetMapping("/receiptsbyfarmerid/{fid}")
	public List<payModel> farmerreceipts(@PathVariable int fid) {
		return adminservices.receiptsbyfarmerid(fid);
	}
	
	@GetMapping("/receiptsbydealerid/{dealerid}")
	public List<payModel> Allfarmerposts(@PathVariable int dealerid)  {
		return adminservices.receiptsbydealerid(dealerid);
	}
	
	@PutMapping("/editcrop/{farmerid}")
	public ResponseEntity<?>  updatefarmerposts(@PathVariable long farmerid, @RequestBody Crops crps) throws Exception {
		return adminservices.updtcropsbyfarmerid(farmerid,crps);
	}
	
	@PostMapping("/addcrop/{croptype}")
	public ResponseEntity<String> addedcrp(@RequestBody Crops crps){
		
		return adminservices.Receivespostsfromfarmer(crps);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

		return adminservices.addimagefromfarmer(file);
	}
	
	@GetMapping(path = { "/get/{imageid}" })
	public CropImages getImage(@PathVariable("imageid") Long imageid) throws IOException {

		return adminservices.getcropimages(imageid);
	}
	
	@GetMapping(path = { "/getimglist" })
	public List<CropImages> getImagelist() throws IOException {

		return adminservices.getcropimageslst();
	}
	
	@GetMapping("/Kharifcropslist")
	public List<Cropsdata> Kharifcropposts()  {
		return adminservices.AllKharifcrops();
	}
	
	@GetMapping("/Rabbicropslist")
	public List<Cropsdata> Rabbicropposts() throws Exception {
		return adminservices.AllRabbicrops();
	}
	
	@GetMapping("/Cashcropslist")
	public List<Cropsdata> Cashcropposts() throws Exception {
		return adminservices.AllCashcrops();
	}
	/*@GetMapping("/viewposts")
	public List<KharifCrops> getfarmerposts() {
		
	}*/
}