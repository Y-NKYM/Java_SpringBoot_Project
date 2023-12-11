package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusKind {
	
	/** 利用可能 */
	ENABLED(false, "利用可能"),
	
	/** 利用不可 */
	DISABLED(true, "利用不可");
	
	private boolean isDisabled;
	private String displayValue;
}
