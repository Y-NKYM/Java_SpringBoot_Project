package com.example.demo.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ToDoListColumn {
	TITLE("タイトル", "title"),
	CREATED_TIME("作成日", "created_time");
	
	private String displayValue;
	private String value;
	
	public static ToDoListColumn findSelectedColumn(String value) {
		return Arrays.stream(ToDoListColumn.values())
				.filter(column -> column
					.value.equals(value))
					.findFirst()
					.orElse(TITLE);
	}
}
