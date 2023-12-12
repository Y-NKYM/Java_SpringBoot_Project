package com.example.demo.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.constant.RegisterMessage;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewHtmlConst;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.RegisterForm;
import com.example.demo.service.RegisterService;
import com.example.demo.util.AppUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(UrlConst.REGISTER)
public class RegisterController {
	
	/** 新規登録画面 Service */
	private final RegisterService service;
	
	/** Message Source */
	private final MessageSource messageSource;
	
	
	
	@GetMapping()
	public String view(Model model, RegisterForm form) {
		return ViewHtmlConst.REGISTER;
	}
	
	@PostMapping()
	public String register(HttpServletRequest request, Model model,@Validated RegisterForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			storeMessage(model, RegisterMessage.VALIDATE_FAILED.getMessageId(), true);
			return ViewHtmlConst.REGISTER;
		}
		
		Optional<UserInfo> user = service.registerUser(form);
		RegisterMessage registerMessage = chooseMessage(user);
		storeMessage(model, registerMessage.getMessageId(), registerMessage.isError());
		if(user.isEmpty()) {
			return ViewHtmlConst.REGISTER;
		} else {
			return ViewHtmlConst.USER_MYPAGE;
		}
	}
	
	private void storeMessage(Model model, String messageId, boolean isError) {
		String message = AppUtil.getMessage(messageSource, messageId);
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
	}
	
	private RegisterMessage chooseMessage(Optional<UserInfo> user) {
		if(user.isEmpty()) {
			return RegisterMessage.EXIST_EMAIL_FAILED;
		} else {
			
			return RegisterMessage.SUCCEED;
		}
	}
	
	
}
