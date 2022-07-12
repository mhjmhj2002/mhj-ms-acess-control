package com.mhj.ms.acess.control.auth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class JwtToken {
	
    @Id
    private String token;

	public JwtToken() {
		super();
	}

    public JwtToken(String token) {
        this.token = token;
    }

}
