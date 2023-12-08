package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.constant.UrlConst;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	/** ユーザー検索変数 */
	private final String USERNAME_PARAMETER = "email";
	
//	/** Password Encoder */
//	private final PasswordEncoder passwordEncoder;
//	
//	/** SpringSecurity用のユーザー情報取得Service */
//	private final UserDetailsService userDetailsService;
//	
//	/** Message Source */
//	private final MessageSource messageSource;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		
		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(UrlConst.NO_AUTH).permitAll()
				.anyRequest().authenticated()
				)
				.formLogin(login -> login
				.loginPage(UrlConst.LOGIN)
				.usernameParameter(USERNAME_PARAMETER)
				.defaultSuccessUrl(UrlConst.USER)
				);
		
		return http.build();	
		
	}
}
