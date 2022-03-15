package com.agronomics.farmersserver.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.agronomics.farmersserver.models.Cropsdata;
import com.agronomics.farmersserver.models.ListCrops;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class FallbackService {

	@Autowired
	RestTemplate restTemplate;
	
		@HystrixCommand(fallbackMethod="fallbackcropserver",commandProperties = {
				   @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
		})
		List<Cropsdata> postedcropslist() {
			return restTemplate.getForObject("http://API-Gateway/agroadmin/admin/cropslist", List.class);
		}
		
		public List<Cropsdata> fallbackcropserver() {
			List<Cropsdata> lc =new ArrayList<>();
			lc.add(new Cropsdata(null,null, "crop server down", null, null, 0, null, null, null, null, null, null, null));
			return lc;
		}
		
		@HystrixCommand(fallbackMethod="fallbackCrops",commandProperties = {
				   @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
		})
		public Cropsdata allcrops(Long cropid) {
			return restTemplate.getForObject("http://API-Gateway/agroadmin/admin/cropslist/cropid="+cropid, Cropsdata.class);
		}
		
		public Cropsdata fallbackCrops(Long cropid) {
			return new Cropsdata(null,null,"Crop server is down try again later","Fallback try again later", null, 0, null, null, null, cropid, null, null, null);
		}
		
		
		@HystrixCommand(fallbackMethod="fallbackcroplistbyid",commandProperties = {
				   @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
		})
		public List<Cropsdata> croplistbyfarmerid(Long farmerid) {
			return restTemplate.getForObject("http://API-Gateway/agroadmin/admin/cropslist/farmerid="+farmerid, List.class);
		}
		
		public List<Cropsdata> fallbackcroplistbyid(Long farmerid) {
			List<Cropsdata> lc =new ArrayList<>();
			lc.add(new Cropsdata(null,null, "crop server down", null, null, 0, null, null, null, null, null, null, null));
			return lc;
		}
}
