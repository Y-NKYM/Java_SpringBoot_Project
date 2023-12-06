package com.example.demo.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.RegisterForm;
import com.example.demo.service.RegisterService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
	
	/** 新規登録画面 Service */
	private final RegisterService service;
	
	/** Message Source */
	private final MessageSource messageSource;
	
	@GetMapping()
	public String view(Model model, RegisterForm form) {
		return "/authenticate/register";
	}
	
	@PostMapping()
	public String register(Model model, RegisterForm form) {
		Optional<UserInfo> user = service.registerUser(form);
		var message = AppUtil.getMessage(messageSource, chooseMessage(user));
		model.addAttribute("message", message);
		if(user.isEmpty()) {
			return "/authenticate/register";
		} else {
			return "/user/mypage";
		}
	}
	
	private String chooseMessage(Optional<UserInfo> user) {
		if(user.isEmpty()) {
			return MessageConst.REGISTER_FAILED;
		} else {
			
			return MessageConst.REGISTER_SUCCEED;
		}
	}
}
