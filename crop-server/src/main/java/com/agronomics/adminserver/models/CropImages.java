package com.agronomics.adminserver.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Table(name = "CropImages")
public class CropImages {

	@Id
	private @Getter @Setter Long id;

	@Column(name = "name")
	private @Getter @Setter String name;

	@Column(name = "type")
	private @Getter @Setter String type;

    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
	@Column(name = "picByte", length = 1000)
	private @Getter @Setter byte[] picByte;


}