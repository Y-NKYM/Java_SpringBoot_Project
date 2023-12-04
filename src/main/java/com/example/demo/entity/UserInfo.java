package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="user")
@Data
public class UserInfo {
	
	@Id
	@Column(name="id")
	private String userId;
	private String name;
	private String email;
	private String password;
	
}
