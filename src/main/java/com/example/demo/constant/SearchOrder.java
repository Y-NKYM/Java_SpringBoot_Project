package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchOrder {
	ASC("昇順", "asc"),
	DESC("降順", "desc");
	
	private String displayValue;
	private String value;
	
	public static SearchOrder findSelectedOrder(String value) {
		return SearchOrder.ASC.getValue().equals(value)?SearchOrder.ASC:SearchOrder.DESC;
	}
}
