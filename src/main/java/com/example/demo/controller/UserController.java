package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
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
import com.example.demo.constant.SearchOrder;
import com.example.demo.constant.ToDoListColumn;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewHtmlConst;
import com.example.demo.entity.ToDoListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SearchForm;
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
	public String view(@ModelAttribute("list") ArrayList<ToDoListInfo> list ,@ModelAttribute("search") SearchForm form, @AuthenticationPrincipal User authUser, Model model) {
		model.addAttribute("toDoListColumn", ToDoListColumn.class);
		model.addAttribute("searchOrder", SearchOrder.class);

		/**
		 * 管理者権限を持っているかをboolean型で判断します。
		 */
		boolean hasAdminAuth = authUser.getAuthorities().stream()
				.allMatch(authority -> authority.getAuthority()
						.equals(AuthorityKind.ADMIN_AUTHORITY.getAuthorityCode()));
		model.addAttribute("hasAdminAuth", hasAdminAuth);
		
		/**
		 * ログインユーザー情報を取得し、@ModelAttributesのlist変数内が
		 * ・空の場合、ユーザーのToDoList一覧を取得。
		 * ・空でない場合、並び替え情報を基に並び替えたToDoList一覧を取得。
		 */
		Optional<UserInfo> user = userService.searchUserByEmail(authUser.getUsername());
		if(!user.isEmpty()){
			UserInfo userInfo = user.get();
			model.addAttribute("user", userInfo);
		
			if(list.isEmpty()) {
				List<ToDoListInfo> toDoLists = toDoListService.getUserToDoLists(userInfo);
				model.addAttribute("toDoLists", toDoLists);
				model.addAttribute("search", false);
			}else {
				SearchOrder selectedOrder = SearchOrder.findSelectedOrder(form.getOrder());
				ToDoListColumn selectedColumn = ToDoListColumn.findSelectedColumn(form.getColumn());
				model.addAttribute("selectedOrder", selectedOrder);
				model.addAttribute("selectedColumn", selectedColumn);
				model.addAttribute("toDoLists", list);
				model.addAttribute("search", true);
			}
		}
		
		return ViewHtmlConst.USER_MYPAGE;
	}
}
