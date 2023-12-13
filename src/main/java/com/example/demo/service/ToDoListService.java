package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ToDoListInfo;

public interface ToDoListService {
	public List<ToDoListInfo> searchListByUserId(String userId);
}
