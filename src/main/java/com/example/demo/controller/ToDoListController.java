package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@RequestMapping(UrlConst.TODO)
public class ToDoListController {
	
	/** ToDoListService */
	private final UserService userService;
	private final ToDoListService toDoListService;
	
	/** CategoryService */
	private final CategoryService categoryService;
	
	/** MessageSource */
	private final MessageSource messageSource;
	
	@GetMapping()
	public String view(Model model, ToDoListNewForm form) {
		List<CategoryInfo> categories = categoryService.getCategories();
		model.addAttribute("categories", categories);
		return ViewHtmlConst.TODO_NEW;
	}
	
	@PostMapping("/create")
	public String create(@AuthenticationPrincipal User authUser, Model model, ToDoListNewForm form) {
		Optional<ToDoListInfo> toDoList = toDoListService.createToDoList(form);
		
		ToDoListMessage toDoListMessage = chooseMessage(toDoList);
		storeMessage(model, toDoListMessage.getMessageId(), toDoListMessage.isError());
		return "redirect:/user";
	}
	
	@PostMapping(params="search")
	public String search(@AuthenticationPrincipal User authUser, RedirectAttributes redirectAttributes, Model model, SearchForm form) {
		Optional<UserInfo> user = userService.searchUserByEmail(authUser.getUsername());
		List<ToDoListInfo> todoLists = toDoListService.orderUserToDoLists(form, user.get());
		redirectAttributes.addFlashAttribute("list", todoLists);
		redirectAttributes.addFlashAttribute("searchKey", form);
		return "redirect:/user";
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
