package com.agronomics.farmersserver.models;

import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CropImages")
public class CropImages {
    @Id
    private @Getter @Setter String imgid;
    private @Getter @Setter String imgtitle;
    private @Getter @Setter Binary image;
}