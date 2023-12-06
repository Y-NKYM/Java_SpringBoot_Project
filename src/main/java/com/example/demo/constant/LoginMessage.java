package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginMessage {
	SUCCEED(MessageConst.LOGIN_SUCCEED, false),
	FAILED(MessageConst.LOGIN_FAILED, true);
	
	private String messageId;
	private boolean isError;
}
