package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.ToDoListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SearchForm;
import com.example.demo.form.ToDoListNewForm;

public interface ToDoListService {
	/**
	 * ユーザー情報テーブルを全件検索し、ユーザー一覧画面に必要な情報へ変換して返します。
	 * @return ユーザー情報テーブルの全登録情報の配列
	 */
	public List<ToDoListInfo> getToDoLists();
	public List<ToDoListInfo> searchToDoListsByParam(SearchForm form);
	public Optional<ToDoListInfo> createToDoList(ToDoListNewForm form);
	public List<ToDoListInfo> orderUserToDoLists(SearchForm form, UserInfo user);
}
