package com.example.demo.entity.converter;

import com.example.demo.constant.ListStatusKind;

import jakarta.persistence.AttributeConverter;

public class ListStatusConverter implements AttributeConverter<ListStatusKind, String>{
	
	/**
	 * 受け取ったListStatusKind内のEnum型をデータベース型（String）に変換します。
	 * @return String(数字)
	 */
	@Override
	public String convertToDatabaseColumn(ListStatusKind listStatusKind) {
		return listStatusKind.getCategoryCode();
	}
	
	/**
	 * データベース内のStringを受け取り、ListStatusKind内のEnum型に変換します。
	 * @return ListStatusKind Enum
	 */
	@Override
	public ListStatusKind convertToEntityAttribute(String value) {
		return ListStatusKind.from(value);
	}
	
}
