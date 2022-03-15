package com.agronomics.adminserver.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Document(collection="RabbiCrops")
public class RabbiCrops {
@Id
private @Getter  @Setter Long cropid;
private @Getter @Setter Long farmerid; 
private @Getter @Setter String farmername;
private @Getter @Setter String cropname;
private @Getter @Setter String cropqty;
private @Getter @Setter int cropprice;
private @Getter @Setter String croplocation;
private @Getter @Setter String croptype;
private @Getter @Setter String cropstatus;

private @Getter @Setter List<Purchases> Requests;

}
