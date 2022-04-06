package com.agronomics.Dealersserver.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.agronomics.Dealersserver.config.MQConfig;

import com.agronomics.Dealersserver.models.Dealers;
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
	 
	
	 
	 public Optional<Dealers> getdealerdetailsbyid(Long id) {
			return dealrepo.findById(id);
		}
	
	
	public Dealers getdealerdetbyname(String dealername) {
		return dealrepo.findBydealername(dealername);
	}
		
	public String subscribefarmer(Long farmerid, Long dealerid) {
		// TODO Auto-generated method stub
		List<Long> sublst= new ArrayList<>();
		Dealers d= dealrepo.findById(dealerid).get();
				if(d.getSubs()==null) {
					sublst.add(farmerid);	
				}else {// if(d.getSubs().stream().filter((fid)->fid==farmerid) == null){
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

	public String deletedealerbyid(Long id) {
		// TODO Auto-generated method stub
		dealrepo.deleteById(id);
		return "record deleted";
	}

	public String updatedealerdet(Dealers dealersdet, Long dealerid) {
		Optional<Dealers> db= dealrepo.findById(dealerid);
		if(db.isPresent()) {
			Dealers d = db.get();
			d.setDemail(d.getDemail());
			d.setDpasswd(d.getDpasswd());
			d.setRoles(d.getRoles());
			d.setDealername(dealersdet.getDealername());
			d.setDphn(dealersdet.getDphn());
			
			dealrepo.save(d);
			return "Dealer updated profile ";
		}
		return "Not updated profile ";
	}

	public String dealerdelete(Long id) {
		// TODO Auto-generated method stub
		dealrepo.deleteById(id);
		return "deleted record";
	}
	
	
}
