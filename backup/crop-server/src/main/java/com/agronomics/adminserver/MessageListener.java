package com.agronomics.adminserver;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agronomics.adminserver.config.MQConfig;
import com.agronomics.adminserver.models.Crops;
import com.agronomics.adminserver.service.AdminServices;

@Component
public class MessageListener {
	
	@Autowired
	AdminServices service;
	
    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(Crops message) {
    	if(message.getOperation().equals("add")) {
    	try {
    		service.Receivespostsfromfarmer(message);	
    	}catch(Exception e) {
    	System.out.println("Exception "+ e.getMessage());
    
    	}
    	
    }else if(message.getOperation().equals("update")) {
    	try {
    		service.updatepostforfarmer(message);	
    	}catch(Exception e) {
    	System.out.println("Exception "+ e.getMessage());
    
    	}
    }else if(message.getOperation().equals("purchase")) {
    	try {
    		service.requestcropfromdealer(message);	
    	}catch(Exception e) {
    	System.out.println("Exception "+ e.getMessage());
    
    	}
    }else if(message.getOperation().equals("updaterequeststatus")) {
    	try {
    		System.out.println("Hello before");
    		service.updaterequestcropfromdealer(message);	
    		System.out.println("Hello after");
    	}catch(Exception e) {
    	System.out.println("Exception "+ e.getMessage());
    
    	}
    }
    }
}