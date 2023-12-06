package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.RegisterForm;

public interface RegisterService {
	
	/**
	 * ユーザー情報データベースに新規ユーザーの登録を行います
	 * 
	 * @param RegisterForm 新規登録入力フォーム
	 * @return UserInfo 登録されたユーザー情報Entity。
	 * 		  （メールアドレスが既に存在する場合はEmpty）
	 */
	public Optional<UserInfo> registerUser(RegisterForm form);
	
}
