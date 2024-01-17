package com.example.demo.dto;

import com.example.demo.constant.ListStatusKind;

import lombok.Data;

@Data
public class TodolistEditDTO {
	private String title;
	private String body;
	private String categoryId;
	private ListStatusKind status;
}
