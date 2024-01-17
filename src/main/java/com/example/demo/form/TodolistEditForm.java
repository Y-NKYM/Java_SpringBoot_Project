package com.example.demo.form;

import com.example.demo.constant.ListStatusKind;

import lombok.Data;

@Data
public class TodolistEditForm {
	private String userId;
	private String title;
	private String body;
	private String categoryId;
	private ListStatusKind status;
}
