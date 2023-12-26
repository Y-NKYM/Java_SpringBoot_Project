package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ToDoListInfo;
import com.example.demo.entity.UserInfo;

public interface ToDoListRepository extends JpaRepository<ToDoListInfo, String>{
	public List<ToDoListInfo> findByOrderByTitleAsc();
	public List<ToDoListInfo> findByUserOrderByTitleAsc(UserInfo user);
}