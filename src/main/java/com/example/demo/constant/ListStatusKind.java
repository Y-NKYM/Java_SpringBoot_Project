package com.example.demo.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ListStatusKind {
	
	/** 未達成：初期 */
	UNTOUCHED("1", "未開始"),
	
	/** 進行中 */
	IN_PROCESS("2", "進行中"),
	
	/** 達成済み */
	COMPLETED("3", "完了");
	
	private String categoryCode;
	private String displayValue;
	
	/**
	 * データベースから取ってきたCategoryの値をListCategoryKind内の各EnumのcateogryCode値と比較し、合ったEnumに変換します。
	 * @param String データベース内のcategoryカラム値
	 * @return ListCategoryKind Enum
	 */
	public static ListStatusKind from(String categoryCode) {
		return Arrays.stream(ListStatusKind.values())
				.filter(listCategoryKind -> listCategoryKind.getCategoryCode().equals(categoryCode))
				.findFirst()
				.orElse(UNTOUCHED);
	}
	
}