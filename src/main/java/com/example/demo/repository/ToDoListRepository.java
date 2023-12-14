package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ToDoListInfo;

public interface ToDoListRepository extends JpaRepository<ToDoListInfo, String>{
	
}