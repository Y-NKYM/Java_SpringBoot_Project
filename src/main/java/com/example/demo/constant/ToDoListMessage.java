package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ToDoListMessage {
	
	SUCCEED(MessageConst.TODOLIST_SUCCEED, false),
	FAILED(MessageConst.TODOLIST_FAILED, true),
	
	UPDATE_SUCCEED(MessageConst.TODOLIST_UPDATE_SUCCEED, false),
	UPDATE_FAILED(MessageConst.TODOLIST_UPDATE_FAILED, true);
	
	private String messageId;
	private boolean isError;
}
