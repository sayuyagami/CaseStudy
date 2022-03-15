package com.agronomics.Dealersserver.services;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.agronomics.Dealersserver.config.MQConfig;
import com.agronomics.Dealersserver.models.Crops;
import com.agronomics.Dealersserver.models.Cropsdata;
import com.agronomics.Dealersserver.models.Dealers;
import com.agronomics.Dealersserver.models.KharifCrops;
import com.agronomics.Dealersserver.models.ListCrops;
import com.agronomics.Dealersserver.models.RabbiCrops;
import com.agronomics.Dealersserver.models.ReceiveMessage;
import com.agronomics.Dealersserver.models.Role;
import com.agronomics.Dealersserver.repository.DealersRepository;

@Service
public class DealerService  implements UserDetailsService{
	@Autowired
	DealersRepository dealrepo;

	@Autowired
	RestTemplate restTemplate;
	
	 @Autowired
	 RabbitTemplate rabbtemplate;
	 
	 @Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	 
	 @Override
	 public UserDetails loadUserByUsername(String demail) throws UsernameNotFoundException {
		 Dealers user = dealrepo.findBydemail(demail);
		 if(user != null) {
		        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		        return buildUserForAuthentication(user, authorities);
		    } else {
		        throw new UsernameNotFoundException("username not found");
		    }
		 //return new User(useremail, password, new ArrayList<>());
	 }
	 
	 private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		    Set<GrantedAuthority> roles = new HashSet<>();
		    userRoles.forEach((role) -> {
		        roles.add(new SimpleGrantedAuthority(role.getRole()));
		    });

		    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		    return grantedAuthorities;
		}
		private UserDetails buildUserForAuthentication(Dealers user, List<GrantedAuthority> authorities) {
		    return new org.springframework.security.core.userdetails.User(user.getDemail(), user.getDpasswd(), authorities);
		}
		
	 public List<Dealers> getdealersdata() {
			return dealrepo.findAll();
	}
	 
	 public Dealers getauthdealerinfo() {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			return dealrepo.findBydemail(auth.getName()); 
	}
	 
	 public ListCrops Allcrops() {
			ListCrops farmercrops = restTemplate.getForObject("http://API-Gateway/agroadmin/admin/cropslist", ListCrops.class);
			return farmercrops;
		}
	 
	 public Optional<Dealers> getdealerdetailsbyid(Long id) {
			return dealrepo.findById(id);
		}
	 
	 //negotiate request
	 public String purchaseforKharifcrop(Long id,Crops reqcrops,Long cropid) {
			//checking whether request for the same crop in dealers purchase list
		 Cropsdata kc = restTemplate.getForObject("http://API-Gateway/agroadmin/admin/cropslist/cropid="+cropid, Cropsdata.class);
		 if(kc!=null) {
			 Long reqid=0L;
			 if(kc.getRequests()!=null) {
				 reqid=(long) kc.getRequests().size();
			 }
			
			 reqcrops.setDealerid(id);
			 reqcrops.setDealername(dealrepo.findById(id).get().getDealername());
			 reqcrops.setNegotiateprice(reqcrops.getNegotiateprice());
			 reqcrops.setPurchaseid(reqid+1);
			 reqcrops.setReqcropid(cropid);
			 reqcrops.setReqstatus("Pending");
			 reqcrops.setOperation("purchase");
			 rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
		                MQConfig.ROUTING_KEY, reqcrops);
			 return "Request sent Successfully through RabbitMQ for "+kc.getCroptype()+" crop";
		 }else {
			 return "Crop does'nt exists";
		 }
	 }
	
	
	public Dealers getdealerdetbyname(String dealername) {
		return dealrepo.findBydealername(dealername);
	}
		/*public Stream<Purchases> getpurchasesbydealerid(Long dealerid) {
		// TODO Auto-generated method stub
		return purchrepo.findAll().stream().filter((dealer)->dealer.getDealerid().equals(dealerid));
	}*/
	public String payforfarmer(String dealername, Long cropid) {
		// TODO Auto-generated method stub
		Cropsdata kc = restTemplate.getForObject("http://API-Gateway/agroadmin/admin/cropslist/cropid="+cropid, Cropsdata.class);
		
		return "Payment By : "+dealername+" Pay : "+kc.getCropprice()+"Crop name :"+kc.getCropname();
	}

	public String subscribefarmer(Long farmerid, Long dealerid) {
		// TODO Auto-generated method stub
		List<Long> sublst= new ArrayList<>();
		Dealers d= dealrepo.findById(dealerid).get();
				if(d.getSubs()==null) {
					sublst.add(farmerid);	
				}else {
					sublst=d.getSubs();
					sublst.add(farmerid);
				}
				d.setSubs(sublst);
				dealrepo.save(d);
				ReceiveMessage msg=new ReceiveMessage();
				msg.setMessagetype("subscribe");
				msg.setDealid(dealerid);
				msg.setFarmid(farmerid);
		 rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, msg);
		 return "Request sent Successfully through RabbitMQ ";
		
	}

	public String unsubscribefarmer(Long farmerid, Long dealerid) {
		// TODO Auto-generated method stub
		
		Dealers d= dealrepo.findById(dealerid).get();
		List<Long> sublst=d.getSubs();
		for(Long i : sublst) {
			if(i==farmerid) {
				sublst.remove(i);
				d.setSubs(sublst);
				dealrepo.save(d);
			}
		}
				ReceiveMessage msg=new ReceiveMessage();
				msg.setMessagetype("unsubscribe");
				msg.setDealid(dealerid);
				msg.setFarmid(farmerid);
		 rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, msg);
		 return "Request sent Successfully through RabbitMQ ";
		
	}
	
	
}
