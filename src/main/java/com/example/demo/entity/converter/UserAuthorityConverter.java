package com.example.demo.entity.converter;

import com.example.demo.constant.AuthorityKind;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserAuthorityConverter implements AttributeConverter<AuthorityKind, String>{
	
	/**
	 * 受け取ったAuthorityKind内のEnum型をデータベース型（String）に変換します。
	 * @return String(数字)
	 */
	@Override
	public String convertToDatabaseColumn(AuthorityKind authorityKind) {
		return authorityKind.getAuthorityCode();
	}
	
	/**
	 * データベース内のStringを受け取り、AuthorityKind内のEnum型に変換します。
	 * @return AuthorityKind Enum
	 */
	@Override
	public AuthorityKind convertToEntityAttribute(String value) {
		return AuthorityKind.from(value);
	}
}
