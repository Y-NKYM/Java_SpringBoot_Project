package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ToDoListInfo;
import com.example.demo.entity.UserInfo;

public interface ToDoListRepository extends JpaRepository<ToDoListInfo, String>{
	public List<ToDoListInfo> findByUser(UserInfo user);
	public List<ToDoListInfo> findByUserOrderByTitleAsc(UserInfo user);
	public List<ToDoListInfo> findByUserOrderByTitleDesc(UserInfo user);
	public List<ToDoListInfo> findByUserOrderByCreatedTimeAsc(UserInfo user);
	public List<ToDoListInfo> findByUserOrderByCreatedTimeDesc(UserInfo user);
	
}