package com.example.demo.constant;

public class EnumMessage {
	
	private String message;
	private boolean isError;
	
	public EnumMessage(String message, boolean isError) {
		this.message = message;
		this.isError = isError;
	}
	
	public static EnumMessage SUCCESS() {
		return new EnumMessage(MessageConst.TODOLIST_SUCCEED, false);
	}
	public static EnumMessage FAILED() {
		return new EnumMessage(MessageConst.TODOLIST_FAILED, true);
	}
}
