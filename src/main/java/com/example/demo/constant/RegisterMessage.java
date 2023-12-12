package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegisterMessage {
	SUCCEED(MessageConst.REGISTER_SUCCEED, false),
	EXIST_EMAIL_FAILED(MessageConst.REGISTER_EXIST_EMAIL_FAILED, true),
	VALIDATE_FAILED(MessageConst.REGISTER_VALIDATE_FAILED, true);
	
	private String messageId;
	private boolean isError;
}
