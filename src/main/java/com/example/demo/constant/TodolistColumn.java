package com.example.demo.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodolistColumn {
	TITLE("タイトル", "title"),
	CREATED_TIME("作成日", "created_time");
	
	private String displayValue;
	private String value;
	
	public static TodolistColumn findSelectedColumn(String value) {
		return Arrays.stream(TodolistColumn.values())
				.filter(column -> column
					.value.equals(value))
					.findFirst()
					.orElse(TITLE);
	}
}
