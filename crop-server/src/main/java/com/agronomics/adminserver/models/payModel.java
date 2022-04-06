package com.agronomics.adminserver.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection="paymentdata")
public class payModel{
   private @Getter @Setter String payid;
    private @Getter @Setter int custid;
    private @Getter @Setter String custname;
    private @Getter @Setter int fid;
    private @Getter @Setter String fname;
    private @Getter @Setter String custreqqty;
    private @Getter @Setter int custamt;
    private @Getter @Setter int cropid;
    private @Getter @Setter String cropname;
   
}