package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TodolistInfo;
import com.example.demo.entity.UserInfo;

public interface TodolistRepository extends JpaRepository<TodolistInfo, String>{
	public List<TodolistInfo> findByUser(UserInfo user);
	public List<TodolistInfo> findByUserOrderByTitleAsc(UserInfo user);
	public List<TodolistInfo> findByUserOrderByTitleDesc(UserInfo user);
	public List<TodolistInfo> findByUserOrderByCreatedTimeAsc(UserInfo user);
	public List<TodolistInfo> findByUserOrderByCreatedTimeDesc(UserInfo user);
	
}