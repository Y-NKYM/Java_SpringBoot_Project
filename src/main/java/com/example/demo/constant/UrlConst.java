package com.example.demo.constant;

/**
 * URL（ルートパス）の定義クラス
 */
public class UrlConst {
	
	/** ログイン画面 */
	public static final String LOGIN = "/login";
	
	/** 新規登録画面 */
	public static final String REGISTER = "/register";
	
	/** ホーム画面 */
	public static final String HOME = "/";
	
	/** ユーザー画面 */
	public static final String USER = "/user";
	
	/** ToDoリスト画面 */
	public static final String TODOLIST = "/todolist";
	public static final String TODOLIST_EDIT = "/todolist/edit";
	public static final String TODOLIST_SHOW = "/todolist/show";
	
	/** 認証不要画面 */
	public static final String[] NO_AUTH = {LOGIN, REGISTER, HOME, "/webjars/**", "/css/**", "/js/**"};
}

