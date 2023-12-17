package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CategoryInfo;
import com.example.demo.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
	
	@Override
	public List<CategoryInfo> getCategories(){
		return categoryRepository.findAll();
	}
	
}
