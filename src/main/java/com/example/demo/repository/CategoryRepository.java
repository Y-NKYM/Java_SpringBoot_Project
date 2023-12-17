package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CategoryInfo;

public interface CategoryRepository extends JpaRepository<CategoryInfo, String>{

}
