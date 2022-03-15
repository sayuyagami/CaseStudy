package com.agronomics.adminserver.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ListCrops {

	private @Getter @Setter List<Cropsdata> cropdata;
	//private @Getter @Setter List<RabbiCrops> rabbi;
}
