package com.example.demo.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorityKind {
	
	UNKNOWN("", "登録内容不正"),
	USER_AUTHORITY("1", "ユーザー"),
	ADMIN_AUTHORITY("2", "管理者");
	
	private String authorityCode; 
	private String displayValue;
	
	/**
	 * データベースから取ってきたAuthorityの値をAuthorityKind内の各EnumのauthorityCode値と比較し、合ったEnumに変換します。
	 * @param String データベース内のauthorityカラム値
	 * @return AuthorityKind Enum
	 */
	public static AuthorityKind from(String authorityCode) {
		return Arrays.stream(AuthorityKind.values())
				.filter(authorityKind -> authorityKind.getAuthorityCode().equals(authorityCode))
				.findFirst()
				.orElse(UNKNOWN);
	}
}
