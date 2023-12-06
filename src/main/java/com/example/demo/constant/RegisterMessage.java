package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegisterMessage {
	SUCCEED(MessageConst.REGISTER_SUCCEED, false),
	FAILED(MessageConst.REGISTER_FAILED, true);
	
	private String messageId;
	private boolean isError;
}
