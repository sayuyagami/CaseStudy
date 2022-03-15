package com.agronomics.farmersserver.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.agronomics.farmersserver.config.MQConfig;
import com.agronomics.farmersserver.models.Crops;
import com.agronomics.farmersserver.models.Farmers;
import com.agronomics.farmersserver.models.KharifCrops;
import com.agronomics.farmersserver.models.ListCrops;
import com.agronomics.farmersserver.models.Purchases;
import com.agronomics.farmersserver.models.RabbiCrops;
import com.agronomics.farmersserver.repository.FarmerRepository;

@Service
public class FarmerService implements UserDetailsService{
	
	@Autowired
	FarmerRepository farmerrepo;
	
	 @Autowired
	 RabbitTemplate rabbtemplate;
	 @Autowired
	 RestTemplate restTemplate;
	 @Autowired
	 private SequenceGeneratorService sequenceGeneratorService;
	 
	@Override
	public UserDetails loadUserByUsername(String femail) throws UsernameNotFoundException {
		Farmers user = farmerrepo.findByfemail(femail);
		if(user== null) return null;
		String useremail = user.getFemail();
		String password = user.getFpasswd();
		
        return new User(useremail, password, new ArrayList<>());
	}
	//adduser
	public String addfarmerdet(Farmers f) {
		farmerrepo.save(f);
		return "Added new User";
	}
	
	//get user details
	public Optional<Farmers> getfarmerdet(Long id) {
		return farmerrepo.findById(id);
	}
	
	public Farmers getauthfarmerinfo() {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		return farmerrepo.findByfemail(auth.getName()); 
	}
	
	public Farmers getfarmerdetbyname(String farmernm) {
		// TODO Auto-generated method stub
		return farmerrepo.findByfarmername(farmernm);
	}
	
	public List<Farmers> Allfarmers() {
		 return farmerrepo.findAll();
	}
	
	public ListCrops Allcrops() {
		ListCrops farmercrops = restTemplate.getForObject("http://admin-service/admin/cropslist", ListCrops.class);
		return farmercrops;
	}
	
	public String updatestatusofreq(Long dealerid, Long cropid, String reqstatus) {
		// TODO Auto-generated method stub
		Purchases p =new Purchases();
		p.setDealerid(dealerid);
		p.setReqcropid(cropid);
		p.setReqstatus(reqstatus);
		rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, p);
		return "Message sent Successfully ";
	}

	
	public String addcrops(Long id,Crops crops, String croptype) {
		crops.setOperation("add");
		if(croptype.equals("Kharif")) {
			Farmers f =farmerrepo.findById(id).get();
			crops.setFarmerid(id);
			crops.setFarmername(f.getFarmername());
			crops.setCroptype("KharifCrops");
			
			rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, crops);
			return "Request sent Successfully through RabbitMQ Added Kharif crop ";
		}else if(croptype.equals("Rabbi")) {
			Farmers f =farmerrepo.findById(id).get();
			crops.setFarmerid(id);
			crops.setFarmername(f.getFarmername());
			crops.setCroptype("RabbiCrops");
			rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, crops);
			return "Request sent Successfully through RabbitMQ Added Rabbi crop ";
		}
		return "Request not sent";
	}

/*	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/imagedata";
	public String addcrops(Long id,Crops crops, MultipartFile file) {
		
		crops.setFarmerid(id);
		crops.setCropid(sequenceGeneratorService.generateSequenceforcrop(Crops.SEQUENCE_NAME));

		StringBuilder fileNames = new StringBuilder();
		String filename=crops.getCropid() + file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);
		Path fileNameAndPath =Paths.get(uploadDirectory,filename);
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		crops.setCropphoto(filename);
		
		croprepo.save(crops);
	
		return "Added crop "+id;
	}*/

	public String updatefarmerdet( Farmers farmersdet, Long farmerid) {
		Optional<Farmers> db= farmerrepo.findById(farmerid);
		if(db.isPresent()) {
			Farmers f = db.get();
			f.setFarmername(farmersdet.getFarmername());
			f.setFphn(farmersdet.getFphn());
			farmerrepo.save(f);
			return "Farmer updated profile ";
		}
		return "Not updated profile ";
	}
	
	
	public String updtcropbyid(Long id, Long cropid, Crops crops,String croptype) {
		crops.setOperation("update");
		if(croptype.equals("Kharif")) {
		KharifCrops kc = restTemplate.getForObject("http://admin-service/admin/cropslist/Kharif/"+cropid, KharifCrops.class);
			kc.setFarmerid(id);
			kc.setCroplocation(crops.getCroplocation());
			kc.setCropprice(crops.getCropprice());
			kc.setCropqty(crops.getCropqty());
			
			rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, crops);
			return "Update Request sent Successfully through RabbitMQ for Kharif crop ";
		}else if(croptype.equals("Rabbi")) {
			RabbiCrops rc = restTemplate.getForObject("http://admin-service/admin/cropslist/Rabbi/"+cropid, RabbiCrops.class);
			rc.setFarmerid(id);
			rc.setCroplocation(crops.getCroplocation());
			rc.setCropprice(crops.getCropprice());
			rc.setCropqty(crops.getCropqty());
			rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, crops);
			return "Update Request sent Successfully through RabbitMQ for Rabbi crop ";
		
		}
		return "Crop details not updated";
	}

	
	public ListCrops displaycropsbyname(Long id, String cropname) {
		ListCrops c= new ListCrops();
		ListCrops lsc=displaycropsforcurrentuser(id);
		List<KharifCrops> kc=lsc.getKharif().stream().filter((kharif)->kharif.getCropname().equals(cropname)).toList();
		List<RabbiCrops> rc =lsc.getRabbi().stream().filter((rabbi)->rabbi.getCropname().equals(cropname)).toList();
		if(kc!=null) {
			c.setKharif(kc);
		}
		if(rc!=null) {
			c.setRabbi(rc);
		}
		return c;
	}
	

	public String delfarmerdet(Long farmerid) {
		farmerrepo.deleteById(farmerid);
		return "User deleted";
	}
	
	
	
	public ListCrops displaycropsforcurrentuser(Long farmerid) {
		// TODO Auto-generated method stub
		ListCrops farmercrops = restTemplate.getForObject("http://admin-service/admin/cropslist/"+farmerid, ListCrops.class);
		return farmercrops;
	}
	public String updtcropreqbyid(Long farmerid, Long cropid, Long reqid, Crops crops, String croptype) {
		crops.setOperation("updaterequeststatus");
		if(croptype.equals("Kharif")) {
				crops.setCropid(cropid);
				crops.setPurchaseid(reqid);
				crops.setCroptype("KharifCrops");
				crops.setCropreqstatus(crops.getCropreqstatus());
			rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, crops);
			return "Update Request status sent Successfully through RabbitMQ for Kharif crop ";
		}else if(croptype.equals("Rabbi")) {
					crops.setCropid(cropid);
					crops.setPurchaseid(reqid);
					crops.setCroptype("RabbiCrops");
					crops.setCropreqstatus(crops.getCropreqstatus());
			
			rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, crops);
			return "Update Request status sent Successfully through RabbitMQ for Rabbi crop ";
		
		}
		return "Crop details not updated";
	}
	
}
