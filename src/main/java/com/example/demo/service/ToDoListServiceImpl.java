package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.constant.ListStatusKind;
import com.example.demo.constant.ToDoListMessage;
import com.example.demo.entity.CategoryInfo;
import com.example.demo.entity.ToDoListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SearchForm;
import com.example.demo.form.ToDoListEditForm;
import com.example.demo.form.ToDoListNewForm;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ToDoListRepository;
import com.example.demo.repository.UserRepository;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoListServiceImpl implements ToDoListService{
	
	/** User・ToDoList・Category Repository */
	private final UserRepository userRepository;
	private final ToDoListRepository toDoListRepository;
	private final CategoryRepository categoryRepository;
	
	/** Dozerの戻り値がMapper型なため */
	private final Mapper mapper;
	
	@Override
	public List<ToDoListInfo> getUserToDoLists(UserInfo user){
		return toDoListRepository.findByUser(user);
	}
	
	@Override
	public Optional<ToDoListInfo> getToDoList(String todolistId){
		return toDoListRepository.findById(todolistId);
	}
	
	@Override
	public List<ToDoListInfo> orderUserToDoLists(SearchForm form, UserInfo user){
		//タイトル・昇順
		if(form.getColumn().equals("title") && form.getOrder().equals("asc")) {
			return toDoListRepository.findByUserOrderByTitleAsc(user);
		//タイトル・降順
		}else if(form.getColumn().equals("title") && form.getOrder().equals("desc")) {
			return toDoListRepository.findByUserOrderByTitleDesc(user);
		//作成日・昇順
		}else if(form.getColumn().equals("created_time") && form.getOrder().equals("asc")) {
			return toDoListRepository.findByUserOrderByCreatedTimeAsc(user);
		//作成日・降順
		}else {
			return toDoListRepository.findByUserOrderByCreatedTimeDesc(user);
		}
	}
	
	@Override
	public Optional<ToDoListInfo> createToDoList(ToDoListNewForm form){
		Optional<UserInfo> existedUser = userRepository.findByEmail(form.getUserId());
		Optional<CategoryInfo> existedCategory = categoryRepository.findById(form.getCategoryId());
		//ユーザーまたはカテゴリーが存在していない場合
		if(existedUser.isEmpty() || existedCategory.isEmpty()) {
			return Optional.empty();
		}
		
		var toDoList = mapper.map(form, ToDoListInfo.class);
		toDoList.setUser(existedUser.get());
		toDoList.setCategory(existedCategory.get());
		toDoList.setStatus(ListStatusKind.UNTOUCHED);
		toDoList.setCreatedTime(LocalDateTime.now());
		toDoList.setUpdatedTime(LocalDateTime.now());
		return Optional.of(toDoListRepository.save(toDoList));
	}
	
	@Override
	public ToDoListMessage updateTodolist(ToDoListEditForm form, String selectedTodolistId) {
		Optional<ToDoListInfo> selectedTodolist = toDoListRepository.findById(selectedTodolistId);
		if(selectedTodolist.isEmpty()) {
			return ToDoListMessage.UPDATE_FAILED;
		}
		Optional<CategoryInfo> category = categoryRepository.findById(form.getCategoryId());
		if(category.isEmpty()) {
			return ToDoListMessage.UPDATE_FAILED;
		}
		ToDoListInfo todolist = selectedTodolist.get();
		todolist.setTitle(form.getTitle());
		todolist.setBody(form.getBody());
		todolist.setCategory(category.get());
		todolist.setStatus(form.getStatus());
		
		try {
			toDoListRepository.save(todolist);
		}catch(Exception e) {
			return ToDoListMessage.UPDATE_FAILED;
		}
		return ToDoListMessage.UPDATE_SUCCEED;
	}
	
	@Override
	public ToDoListMessage deleteTodolist(String selectedTodolistId) {
		var todolist = toDoListRepository.findById(selectedTodolistId);
		if(todolist.isEmpty()) {
			return ToDoListMessage.DELETE_FAILED;
		}
		toDoListRepository.deleteById(selectedTodolistId);
		return ToDoListMessage.DELETE_SUCCEED;
	}
	
}
