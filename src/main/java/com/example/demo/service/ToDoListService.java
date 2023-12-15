package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ToDoListInfo;

public interface ToDoListService {
	/**
	 * ユーザー情報テーブルを全件検索し、ユーザー一覧画面に必要な情報へ変換して返します。
	 * @return ユーザー情報テーブルの全登録情報の配列
	 */
	public List<ToDoListInfo> getToDoLists();
}
