package com.agronomics.farmersserver.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ListCrops {

	private @Getter @Setter List<Cropsdata> cropdata;
	
}
