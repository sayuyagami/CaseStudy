package com.agronomics.Dealersserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

  
	private  String dealername,demail,dpasswd;
	private  long dphn;	
}