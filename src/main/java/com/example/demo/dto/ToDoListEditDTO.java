package com.example.demo.dto;

import lombok.Data;

@Data
public class ToDoListEditDTO {
	private String title;
	private String body;
	private String categoryId;
}
