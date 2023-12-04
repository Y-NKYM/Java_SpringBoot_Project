package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.UserInfo;

public interface LoginService {
	
	/**
	 * メールアドレスを元にデータベース検索を行い、ユーザー情報Entityを返します。
	 * 
	 * <p>ただしデータベースに存在しない場合は空オブジェクトを返します。</p>
	 * 
	 * @param email メールアドレス
	 * @return UserInfo 登録情報（ユーザーの情報Entity）
	 */
	public Optional<UserInfo> searchUserByEmail(String email);
}
