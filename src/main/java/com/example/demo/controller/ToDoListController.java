package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.SearchOrder;
import com.example.demo.constant.ToDoListColumn;
import com.example.demo.constant.ToDoListMessage;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewHtmlConst;
import com.example.demo.entity.CategoryInfo;
import com.example.demo.entity.ToDoListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SearchForm;
import com.example.demo.form.ToDoListNewForm;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ToDoListService;
import com.example.demo.service.UserService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(UrlConst.TODOLIST)
public class ToDoListController {
	
	/** ToDoListService */
	private final UserService userService;
	private final ToDoListService toDoListService;
	
	/** CategoryService */
	private final CategoryService categoryService;
	
	/** MessageSource */
	private final MessageSource messageSource;
	
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
		return ViewHtmlConst.TODOLIST;
	}
	
	@GetMapping("/new")
	public String view(Model model, ToDoListNewForm form) {
		List<CategoryInfo> categories = categoryService.getCategories();
		model.addAttribute("categories", categories);
		return ViewHtmlConst.TODOLIST_NEW;
	}
	
	@PostMapping("/create")
	public String create(@AuthenticationPrincipal User authUser, Model model, ToDoListNewForm form) {
		Optional<ToDoListInfo> toDoList = toDoListService.createToDoList(form);
		
		ToDoListMessage toDoListMessage = chooseMessage(toDoList);
		storeMessage(model, toDoListMessage.getMessageId(), toDoListMessage.isError());
		return AppUtil.doRedirect(UrlConst.TODOLIST);
	}
	
	/**
	 * ToDoListの並び順を変更し、マイページへリダイレクトする。
	 * 
	 * @param authUser　ログインユーザー情報を取得する。
	 * @param redirectAttributes　並び替えたToDoListと並び替え情報をリダイレクトするため。
	 * @param form　HTMLから並び替え情報を取得する。
	 * @return　"/user"へリダイレクト。
	 */
	@PostMapping(params="search")
	public String search(@AuthenticationPrincipal User authUser, RedirectAttributes redirectAttributes, SearchForm form) {
		Optional<UserInfo> user = userService.searchUserByEmail(authUser.getUsername());
		List<ToDoListInfo> todoLists = toDoListService.orderUserToDoLists(form, user.get());
		redirectAttributes.addFlashAttribute("list", todoLists);
		redirectAttributes.addFlashAttribute("search", form);
		return AppUtil.doRedirect(UrlConst.TODOLIST);
	}
	
	private void storeMessage(Model model, String messageId, boolean isError) {
		String message = AppUtil.getMessage(messageSource, messageId);
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
	}
	
	private ToDoListMessage chooseMessage(Optional<ToDoListInfo> toDoList) {
		if(toDoList.isEmpty()){
			return ToDoListMessage.FAILED;
		} else {
			return ToDoListMessage.SUCCEED;
		}
	}
	
}
