package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.constant.ListStatusKind;
import com.example.demo.constant.TodolistMessage;
import com.example.demo.entity.CategoryInfo;
import com.example.demo.entity.TodolistInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SearchForm;
import com.example.demo.form.TodolistEditForm;
import com.example.demo.form.TodolistNewForm;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TodolistRepository;
import com.example.demo.repository.UserRepository;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodolistServiceImpl implements TodolistService{
	
	/** User・ToDoList・Category Repository */
	private final UserRepository userRepository;
	private final TodolistRepository todolistRepository;
	private final CategoryRepository categoryRepository;
	
	/** Dozerの戻り値がMapper型なため */
	private final Mapper mapper;
	
	@Override
	public List<TodolistInfo> getUserTodolists(UserInfo user){
		return todolistRepository.findByUser(user);
	}
	
	@Override
	public Optional<TodolistInfo> getTodolist(String todolistId){
		return todolistRepository.findById(todolistId);
	}
	
	@Override
	public List<TodolistInfo> orderUserTodolists(SearchForm form, UserInfo user){
		//タイトル・昇順
		if(form.getColumn().equals("title") && form.getOrder().equals("asc")) {
			return todolistRepository.findByUserOrderByTitleAsc(user);
		//タイトル・降順
		}else if(form.getColumn().equals("title") && form.getOrder().equals("desc")) {
			return todolistRepository.findByUserOrderByTitleDesc(user);
		//作成日・昇順
		}else if(form.getColumn().equals("created_time") && form.getOrder().equals("asc")) {
			return todolistRepository.findByUserOrderByCreatedTimeAsc(user);
		//作成日・降順
		}else {
			return todolistRepository.findByUserOrderByCreatedTimeDesc(user);
		}
	}
	
	@Override
	public Optional<TodolistInfo> createTodolist(TodolistNewForm form){
		Optional<UserInfo> existedUser = userRepository.findByEmail(form.getUserId());
		Optional<CategoryInfo> existedCategory = categoryRepository.findById(form.getCategoryId());
		//ユーザーまたはカテゴリーが存在していない場合
		if(existedUser.isEmpty() || existedCategory.isEmpty()) {
			return Optional.empty();
		}
		
		var todolist = mapper.map(form, TodolistInfo.class);
		todolist.setUser(existedUser.get());
		todolist.setCategory(existedCategory.get());
		todolist.setStatus(ListStatusKind.UNTOUCHED);
		todolist.setCreatedTime(LocalDateTime.now());
		todolist.setUpdatedTime(LocalDateTime.now());
		return Optional.of(todolistRepository.save(todolist));
	}
	
	@Override
	public TodolistMessage updateTodolist(TodolistEditForm form, String selectedTodolistId) {
		Optional<TodolistInfo> selectedTodolist = todolistRepository.findById(selectedTodolistId);
		if(selectedTodolist.isEmpty()) {
			return TodolistMessage.UPDATE_FAILED;
		}
		Optional<CategoryInfo> category = categoryRepository.findById(form.getCategoryId());
		if(category.isEmpty()) {
			return TodolistMessage.UPDATE_FAILED;
		}
		TodolistInfo todolist = selectedTodolist.get();
		todolist.setTitle(form.getTitle());
		todolist.setBody(form.getBody());
		todolist.setCategory(category.get());
		todolist.setStatus(form.getStatus());
		
		try {
			todolistRepository.save(todolist);
		}catch(Exception e) {
			return TodolistMessage.UPDATE_FAILED;
		}
		return TodolistMessage.UPDATE_SUCCEED;
	}
	
	@Override
	public TodolistMessage deleteTodolist(String selectedTodolistId) {
		var todolist = todolistRepository.findById(selectedTodolistId);
		if(todolist.isEmpty()) {
			return TodolistMessage.DELETE_FAILED;
		}
		todolistRepository.deleteById(selectedTodolistId);
		return TodolistMessage.DELETE_SUCCEED;
	}
	
}
