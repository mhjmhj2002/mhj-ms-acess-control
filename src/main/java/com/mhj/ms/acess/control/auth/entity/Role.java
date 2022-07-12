package com.mhj.ms.acess.control.auth.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String descricao;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", 
	joinColumns = {@JoinColumn(name = "role_id") }, 
	inverseJoinColumns = {@JoinColumn(name = "user_id") })
	private List<User> users;

}
