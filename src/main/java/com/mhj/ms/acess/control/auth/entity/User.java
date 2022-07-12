package com.mhj.ms.acess.control.auth.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class User {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Email(message = "*Please provide a valid email")
    @NotEmpty(message = "*Please provide an email")
    private String email;
    
    @NotEmpty(message = "*Please provide your name")
    private String password;
    
    @NotEmpty(message = "*Please provide your name")
    private String name;
    
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;
    
    private Integer active=1;
    
    private boolean isLoacked=false;
    
    private boolean isExpired=false;
    
    private boolean isEnabled=true;
    
    @ManyToMany(mappedBy="users", cascade = CascadeType.ALL)
    private List<Role> roles;

}
