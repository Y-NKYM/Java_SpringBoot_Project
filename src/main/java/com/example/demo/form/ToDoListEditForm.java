package com.example.demo.form;

import com.example.demo.constant.ListStatusKind;

import lombok.Data;

@Data
public class ToDoListEditForm {
	private String userId;
	private String title;
	private String body;
	private String categoryId;
	private ListStatusKind status;
}
