package com.example.demo.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.constant.LoginMessage;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewHtmlConst;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(UrlConst.LOGIN)
public class LoginController {
	
	/** ログイン画面Service */
	private final LoginService service;
	
	/** Password Encoder */
	private final PasswordEncoder passwordEncoder;
	
	/** Message Source */
	private final MessageSource messageSource;
	
	/** セッション情報 */
	private final HttpSession session;
	
	@GetMapping()
	public String view(Model model, LoginForm form) {
		return "/authenticate/login";
	}
	
	@GetMapping(params = "error")
	public String viewWithError(Model model, LoginForm form) {
		var errorInfo = (Exception)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		model.addAttribute("message", errorInfo.getMessage());
		model.addAttribute("isError", true);
		return ViewHtmlConst.LOGIN;
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
		LoginMessage loginMessage = chooseMessage(isCorrectUserAuth);
		String message = AppUtil.getMessage(messageSource, loginMessage.getMessageId());
		model.addAttribute("isError", loginMessage.isError());
		model.addAttribute("message", message);
		if(isCorrectUserAuth) {
			return "/user/mypage";
		}else {
			return ViewHtmlConst.LOGIN;
		}
	}
	
	private LoginMessage chooseMessage(boolean isCorrectUserAuth) {
		if(isCorrectUserAuth) {
			return LoginMessage.SUCCEED;
		} else {
			
			return LoginMessage.FAILED;
		}
	}
}
