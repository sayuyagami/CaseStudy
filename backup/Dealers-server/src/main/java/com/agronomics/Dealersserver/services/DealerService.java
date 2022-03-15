package com.agronomics.Dealersserver.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.agronomics.Dealersserver.config.MQConfig;
import com.agronomics.Dealersserver.models.Crops;
import com.agronomics.Dealersserver.models.Dealers;
import com.agronomics.Dealersserver.models.KharifCrops;
import com.agronomics.Dealersserver.models.RabbiCrops;
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
		 if(user== null) return null;
		 String useremail = user.getDemail();
		 String password = user.getDpasswd();

		 return new User(useremail, password, new ArrayList<>());
	 }
	 public List<Dealers> getdealersdata() {
			return dealrepo.findAll();
		}
	 
	 public Optional<Dealers> getdealerdetailsbyid(Long id) {
			return dealrepo.findById(id);
		}
	 
	 //negotiate request
	 public String purchaseforKharifcrop(Long id,Crops reqcrops,Long cropid) {
			//checking whether request for the same crop in dealers purchase list
		 KharifCrops kc = restTemplate.getForObject("http://admin-service/admin/cropslist/Kharif/"+cropid, KharifCrops.class);
		 if(kc!=null) {
			 Long reqid=0L;
			 if(kc.getRequests()!=null) {
				 reqid=(long) kc.getRequests().size();
			 }
			 Dealers d =new Dealers();
			 reqcrops.setDealerid(id);
			 reqcrops.setDealername(dealrepo.findById(id).get().getDealername());
			 reqcrops.setNegotiateprice(reqcrops.getNegotiateprice());
			 reqcrops.setPurchaseid(reqid+1);
			 reqcrops.setCroptype("KharifCrops");
			 reqcrops.setReqcropid(cropid);
			 reqcrops.setReqstatus("Pending");
			 reqcrops.setOperation("purchase");
			 rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
		                MQConfig.ROUTING_KEY, reqcrops);
			 return "Request sent Successfully through RabbitMQ for Kharif crop";
		 }else {
			 return "Crop does'nt exists";
		 }
	 }
	
	
	public Dealers getdealerdetbyname(String dealername) {
		return dealrepo.findBydealername(dealername);
	}

	public String purchaseforRabbicrop(Long id, Crops reqcrops, Long cropid) {
		RabbiCrops rc = restTemplate.getForObject("http://admin-service/admin/cropslist/Rabbi/"+cropid, RabbiCrops.class);
		 if(rc!=null) {
			 Long reqid=0L;
			 if(rc.getRequests()!=null) {
				 reqid=(long) rc.getRequests().size();
			 }
			 Dealers d =new Dealers();
			 reqcrops.setDealerid(id);
			 reqcrops.setDealername(dealrepo.findById(id).get().getDealername());
			 reqcrops.setNegotiateprice(reqcrops.getNegotiateprice());
			 reqcrops.setPurchaseid(reqid+1);
			 reqcrops.setCroptype("RabbiCrops");
			 reqcrops.setReqcropid(cropid);
			 reqcrops.setReqstatus("Pending");
			 reqcrops.setOperation("purchase");
			 rabbtemplate.convertAndSend(MQConfig.EXCHANGE,
		                MQConfig.ROUTING_KEY, reqcrops);
			 return "Request sent Successfully through RabbitMQ for Rabbi Crop";
		 }else {
			 return "Crop does'nt exists";
		 }
	}

		/*public Stream<Purchases> getpurchasesbydealerid(Long dealerid) {
		// TODO Auto-generated method stub
		return purchrepo.findAll().stream().filter((dealer)->dealer.getDealerid().equals(dealerid));
	}*/

	
}
