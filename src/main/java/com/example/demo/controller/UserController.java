package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewHtmlConst;
import com.example.demo.entity.ToDoListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.ToDoListService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(UrlConst.USER)
public class UserController {
	
	/** ログイン画面Service */
	private final UserService userService;
	private final ToDoListService toDoListService;
	
	@GetMapping()
	public String view(@AuthenticationPrincipal User authUser, Model model) {
		
		/**
		 * 管理者権限を持っているかをboolean型で判断します。
		 */
		boolean hasAdminAuth = authUser.getAuthorities().stream()
				.allMatch(authority -> authority.getAuthority()
						.equals(AuthorityKind.ADMIN_AUTHORITY.getAuthorityCode()));
		model.addAttribute("hasAdminAuth", hasAdminAuth);
		
		Optional<UserInfo> user = userService.searchUserByEmail(authUser.getUsername());
		List<ToDoListInfo> toDoList = toDoListService.searchListByUserId(user.get().getUserId());
		model.addAttribute("user", user);
		model.addAttribute("toDoList", toDoList);
		
		return ViewHtmlConst.USER_MYPAGE;
	}
}
