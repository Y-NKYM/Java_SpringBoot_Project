package com.example.demo.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * アプリケーション共通クラス
 */
public class AppUtil {
	/**
	 * @param messageSource
	 * @param key
	 * @param params
	 * @return value
	 */
	public static String getMessage(MessageSource messageSource, 
			String key, Object...params) {
		return messageSource.getMessage(key, params, Locale.JAPAN);
	}
}
