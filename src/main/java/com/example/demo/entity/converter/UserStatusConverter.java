package com.example.demo.entity.converter;

import com.example.demo.constant.UserStatusKind;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter implements AttributeConverter<UserStatusKind, Boolean>{
	
	/**
	 * 受け取ったUserStatusKind内のEnum型をデータベース型（Boolean）に変換します。
	 * @return Boolean 
	 */
	@Override
	public Boolean convertToDatabaseColumn(UserStatusKind userStatus) {
		return userStatus.isDisabled();
	}
	
	/**
	 * データベース内のBooleanを受け取り、UserStatusKind内のEnum型に変換します。
	 * @return UserStatusKind Enum
	 */
	@Override
	public UserStatusKind convertToEntityAttribute(Boolean isDisabled) {
		return isDisabled ? UserStatusKind.DISABLED:UserStatusKind.ENABLED;
	}
}
