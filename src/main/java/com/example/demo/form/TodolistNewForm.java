package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TodolistNewForm {
	private String userId;
	
	@NotEmpty
	@Size(max=20, message="{max}桁以下でご入力ください。")
	private String title;
	private String body;
	private String categoryId;
	
}
