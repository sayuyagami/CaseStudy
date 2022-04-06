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
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.agronomics.farmersserver.models.CropImages;
import com.agronomics.farmersserver.models.Cropsdata;
import com.agronomics.farmersserver.models.Farmers;
import com.agronomics.farmersserver.models.ReceiveMessage;
import com.agronomics.farmersserver.models.Role;
import com.agronomics.farmersserver.repository.CropImagesRepository;
import com.agronomics.farmersserver.repository.FarmerRepository;

@Service
public class FarmerService implements UserDetailsService{
	
	@Autowired
	FarmerRepository farmerrepo;
	
	@Autowired
	CropImagesRepository imagerepo;
	
	 @Autowired
	 RabbitTemplate rabbtemplate;
	 @Autowired
	 RestTemplate restTemplate;
	 
	 @Autowired
	 FallbackService fallbackservice;

	@Override
	public UserDetails loadUserByUsername(String femail) throws UsernameNotFoundException {
		Farmers user = farmerrepo.findByfemail(femail);
		 if(user != null) {
		        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		        return buildUserForAuthentication(user, authorities);
		    } else {
		        throw new UsernameNotFoundException("username not found");
		    }
		
       
	}
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
	    Set<GrantedAuthority> roles = new HashSet<>();
	    userRoles.forEach((role) -> {
	        roles.add(new SimpleGrantedAuthority(role.getRole()));
	    });

	    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
	    return grantedAuthorities;
	}
	private UserDetails buildUserForAuthentication(Farmers user, List<GrantedAuthority> authorities) {
	    return new org.springframework.security.core.userdetails.User(user.getFemail(), user.getFpasswd(), authorities);
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
	
	@SuppressWarnings("unchecked")
	public List<String> Allcroptypes() throws Exception {
		return restTemplate.getForObject("http://admin-service/admin/croptypes", List.class); 
	}
	
	public List<Farmers> Allfarmers() {
		 return farmerrepo.findAll();
	}
	
	public List<Cropsdata> Allcrops() {
		List<Cropsdata> farmercrops = fallbackservice.postedcropslist();
		return farmercrops;
	}

	public String addcropimage(String cropid,MultipartFile file) throws IOException { 
        CropImages photo = new CropImages(); 
        photo.setImgid(cropid);
        photo.setImage(
          new Binary(BsonBinarySubType.BINARY, file.getBytes())); 
        imagerepo.insert(photo); 
        return "photo uploaded";
    }

    public CropImages getPhoto(String id) { 
        return imagerepo.findById(id).get(); 
    }

	public String updatefarmerdet( Farmers farmersdet, Long farmerid) {
		Optional<Farmers> db= farmerrepo.findById(farmerid);
		if(db.isPresent()) {
			Farmers f = db.get();
			f.setFemail(f.getFemail());
			f.setFpasswd(f.getFpasswd());
			f.setRoles(f.getRoles());
			f.setFarmername(farmersdet.getFarmername());
			f.setFphn(farmersdet.getFphn());
			farmerrepo.save(f);
			return "Farmer updated profile ";
		}
		return "Not updated profile ";
	}
	
	
	public String delfarmerdet(Long farmerid) {
		farmerrepo.deleteById(farmerid);
		return "User deleted";
	}
	
	public  List<Cropsdata> displaycropsforcurrentuser(Long farmerid) {
		// TODO Auto-generated method stub
		 List<Cropsdata> farmercrops = fallbackservice.croplistbyfarmerid(farmerid);
		if(farmercrops!=null) {
			return farmercrops;
		}
		return farmercrops;
	}
	public void subscribedealer(ReceiveMessage message) {
		Farmers f=farmerrepo.findById(message.getFarmid()).get();
		List<Long> sublst= new ArrayList<>();
		
				if(f.getSubs()==null) {
					sublst.add(message.getDealid());	
				}else {
					sublst=f.getSubs();
					sublst.add(message.getDealid());
				}
				f.setSubs(sublst);
				farmerrepo.save(f);
		// TODO Auto-generated method stub
	}
	public void unsubscribedealer(ReceiveMessage message) {
		// TODO Auto-generated method stub
		Farmers f=farmerrepo.findById(message.getFarmid()).get();
		List<Long> sublst= f.getSubs();
		for(Long i : sublst) {
			if(i==message.getDealid()) {
				sublst.remove(i);
				f.setSubs(sublst);
				farmerrepo.save(f);
			}
		}	
	}
	public String farmerdelete(Long id) {
		// TODO Auto-generated method stub
		farmerrepo.deleteById(id);
		return "record deleted";
		}

	/*public String updtcropreqbyid(Long farmerid, Long cropid, Long reqid, Crops crops, String croptype) {
		crops.setOperation("updaterequeststatus");
		Cropsdata kc = fallbackservice.allcrops(cropid);
		if(kc!=null) {
				crops.setCropid(cropid);
				crops.setPurchaseid(reqid);
				crops.setCropreqstatus(crops.getCropreqstatus());
			rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, crops);
			return "Update Request status message sent Successfully for "+kc.getCroptype();
		}
		return "Crop details not updated";
	}*/
	
}
