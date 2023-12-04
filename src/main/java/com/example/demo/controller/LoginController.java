package com.example.demo.controller;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
	
	/** ログイン画面Service */
	private final LoginService service;
	
	/** Password Encoder */
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping()
	public String view(Model model, LoginForm form) {
		return "/authenticate/login";
	}
	
	@PostMapping()
	public String login(Model model, LoginForm form) {
		Optional<UserInfo> user = service.searchUserByEmail(form.getEmail());
		
		/*
		 * 下記により記述されたパスワードのエンコード時の文字列がわかります。
		 * var encodedPassword = passwordEncoder.encode(form.getPassword());
		 */
		
		boolean isCorrectUserAuth = user.isPresent() 
				&& passwordEncoder.matches(form.getPassword(), user.get().getPassword());
	
		if(isCorrectUserAuth) {
			return "redirect:/user";
		}else {
			model.addAttribute("errorMsg", "メールアドレスが存在しないか、パスワードが間違っています。");
			return "/authenticate/login";
		}
	}
}
