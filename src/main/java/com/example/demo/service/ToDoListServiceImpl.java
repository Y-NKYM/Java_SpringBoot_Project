package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.constant.ListStatusKind;
import com.example.demo.entity.CategoryInfo;
import com.example.demo.entity.ToDoListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.ToDoListForm;
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
	public List<ToDoListInfo> getToDoLists(){
		return toDoListRepository.findAll();
	}
	
	@Override
	public Optional<ToDoListInfo> createToDoList(ToDoListForm form){
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
	
}
