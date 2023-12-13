package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ToDoListInfo;
import com.example.demo.repository.ToDoListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoListServiceImpl implements ToDoListService{
	
	private final ToDoListRepository repository;
	
	@Override
	public List<ToDoListInfo> searchListByUserId(String userId){
		var result = repository.findByUserId(userId);
		return result;
	}
}
