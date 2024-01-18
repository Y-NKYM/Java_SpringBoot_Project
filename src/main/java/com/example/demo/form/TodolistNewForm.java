package com.example.demo.form;

import lombok.Data;

@Data
public class TodolistNewForm {

	private String userId;
	private String title;
	private String body;
	private String categoryId;
	
}
