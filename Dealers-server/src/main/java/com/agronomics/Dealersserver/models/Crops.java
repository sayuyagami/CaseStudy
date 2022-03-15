package com.agronomics.Dealersserver.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Crops {
	@Id
	private @Getter  @Setter Long cropid;
	private @Getter  @Setter Long farmerid;
	private @Getter @Setter String operation;
	private  @Setter @Getter Long purchaseid;
	private @Getter @Setter Long dealerid;
	private @Getter @Setter String dealername;
	private @Getter @Setter Long reqcropid;
	private @Getter @Setter String reqqty;
	private @Getter @Setter String croptype;
	private @Getter @Setter int Negotiateprice;
	private @Getter @Setter String reqstatus;
	
	//private @Getter @Setter String cropphoto;
	private @Getter @Setter List<Purchases> Requests;
}
