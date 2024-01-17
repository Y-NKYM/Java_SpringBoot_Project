package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.constant.TodolistMessage;
import com.example.demo.entity.TodolistInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SearchForm;
import com.example.demo.form.TodolistEditForm;
import com.example.demo.form.TodolistNewForm;

public interface TodolistService {
	/**
	 * ユーザー情報テーブルを全件検索し、ユーザー一覧画面に必要な情報へ変換して返します。
	 * @return ユーザー情報テーブルの全登録情報の配列
	 */
	public List<TodolistInfo> getUserTodolists(UserInfo user);
	public Optional<TodolistInfo> getTodolist(String todolistId);
	public Optional<TodolistInfo> createTodolist(TodolistNewForm form);
	public List<TodolistInfo> orderUserTodolists(SearchForm form, UserInfo user);
	public TodolistMessage updateTodolist(TodolistEditForm form, String selectedTodolistId);
	public TodolistMessage deleteTodolist(String selectedTodolistId);
	
	
}
