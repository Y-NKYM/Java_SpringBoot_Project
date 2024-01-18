package com.example.demo.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
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
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(UrlConst.REGISTER)
public class RegisterController {
	
	/** 新規登録画面 Service */
	private final RegisterService service;
	
	/** Message Source */
	private final MessageSource messageSource;
	
	private final AuthenticationManager authenticationManager;
	
	private final SecurityContextRepository securityContextRepository;
	
	@GetMapping()
	public String view(Model model, RegisterForm form) {
		return ViewHtmlConst.REGISTER;
	}
	
	@PostMapping()
	public String register(HttpServletRequest request, HttpServletResponse response, Model model,@Validated RegisterForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			storeMessage(model, RegisterMessage.VALIDATE_FAILED.getMessageId(), RegisterMessage.VALIDATE_FAILED.isError());
			return ViewHtmlConst.REGISTER;
		}
		
		Optional<UserInfo> user = service.registerUser(form);
		RegisterMessage registerMessage = chooseMessage(user);
		storeMessage(model, registerMessage.getMessageId(), registerMessage.isError());
		if(user.isEmpty()) {
			return ViewHtmlConst.REGISTER;
		} else {
			
			String username = user.get().getEmail();
			String password = form.getPassword();
			
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("1"));
			
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, authorities);
			Authentication authentication = authenticationManager.authenticate(token);
					
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
			securityContextRepository.saveContext(context, request, response);
			
		    return "redirect:/user";
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
