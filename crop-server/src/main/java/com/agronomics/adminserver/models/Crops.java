package com.agronomics.adminserver.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.bson.types.Binary;
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
	
	private @Getter @Setter String farmername; 
	private @Getter @Setter String cropname;
	private @Getter @Setter String cropqty;
	private @Getter @Setter int cropprice;
	private @Getter @Setter String cropreqstatus;
	private @Getter @Setter String croptype;
	private @Getter @Setter String croplocation;
	
	//private @Getter @Setter String cropphoto;
	
}
