package com.agronomics.farmersserver.config;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agronomics.farmersserver.models.Crops;
import com.agronomics.farmersserver.models.ReceiveMessage;
import com.agronomics.farmersserver.services.FarmerService;

@Component
public class MessageListener {
	
	@Autowired
	FarmerService service;
	
    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(ReceiveMessage message) {
    	if(message.getMessagetype().equals("subscribe")) {
    		System.out.println(message);	
        	service.subscribedealer(message);	
    	}else {
    		System.out.println(message);	
        	service.unsubscribedealer(message);
    	}
    	
    }
    	
}