package com.example.demo.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * アプリケーション共通クラス
 */
public class AppUtil {
	
	/**
	 * messages.properties内に存在するメッセージをパラメーターで選択し、メッセージを返します。
	 * 
	 * @param messageSource messages.properties内のメッセージ解読用のインターフェース。
	 * @param key どのメッセージを取得するか指定する。
	 * @param params メッセージ反映にパラメーターが必要な場合記述する。
	 * @return message 返すメッセージString型文章。
	 */
	public static String getMessage(MessageSource messageSource, 
			String key, Object...params) {
		return messageSource.getMessage(key, params, Locale.JAPAN);
	}
	
	/**
	 * コントローラーへのルートパスとしてリダイレクト用の記述に変更します。
	 * 
	 * @param path どのコントローラーへリダイレクトしたいかのルートパスを指定する。
	 * @return リダイレクト用に記述したルートパスに変換する。
	 */
	public static String doRedirect(String path) {
		return "redirect:" + path;
	}
}
