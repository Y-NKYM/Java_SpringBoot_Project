package com.example.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@Transactional
public class UserController {
	
	/** UserService */
	private final UserService userService;
	
	/** ToDoListService */
	private final ToDoListService toDoListService;
	
	@GetMapping()
	public String view(@ModelAttribute("list") ArrayList<ToDoListInfo> list ,@AuthenticationPrincipal User authUser, Model model) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("タイトル", "title");
		map.put("作成日", "created_time");
		
		System.out.println(map.entrySet());
		for(Map.Entry<String, String> m : map.entrySet()) {
			System.out.println(m.getValue());
			System.out.println(m.getKey());
		}
		model.addAttribute("map", map);

		/**
		 * 管理者権限を持っているかをboolean型で判断します。
		 */
		boolean hasAdminAuth = authUser.getAuthorities().stream()
				.allMatch(authority -> authority.getAuthority()
						.equals(AuthorityKind.ADMIN_AUTHORITY.getAuthorityCode()));
		model.addAttribute("hasAdminAuth", hasAdminAuth);
		
		Optional<UserInfo> user = userService.searchUserByEmail(authUser.getUsername());
		UserInfo userInfo = user.get();
		if(!userInfo.getToDoLists().isEmpty()){
			model.addAttribute("user", userInfo);
		}
		
		var toDoLists = toDoListService.getToDoLists();
		if(!toDoLists.isEmpty()) {
			model.addAttribute("toDoLists", toDoLists);
		}
		
		return ViewHtmlConst.USER_MYPAGE;
	}
}
