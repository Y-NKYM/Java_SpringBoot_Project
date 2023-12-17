package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CategoryInfo;

public interface CategoryService {
	/**
	 * カテゴリーテーブル情報を全件検索
	 * @return カテゴリーテーブル情報の配列
	 */
	public List<CategoryInfo> getCategories();
}
