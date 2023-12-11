package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorityKind {
	
	USER_AUTHORITY("1"),
	ADMIN_AUTHORITY("2");
	
	private String authorityCode; 
}
