package com.agronomics.farmersserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String femail,farmername;
    private String fpasswd;
    private long fphn;
    
}