package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.ListStatusKind;
import com.example.demo.constant.SearchOrder;
import com.example.demo.constant.SessionKeyConst;
import com.example.demo.constant.TodolistColumn;
import com.example.demo.constant.TodolistMessage;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewHtmlConst;
import com.example.demo.dto.ToDoListEditDTO;
import com.example.demo.entity.CategoryInfo;
import com.example.demo.entity.TodolistInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SearchForm;
import com.example.demo.form.SelectedIdForm;
import com.example.demo.form.TodolistEditForm;
import com.example.demo.form.TodolistNewForm;
import com.example.demo.service.CategoryService;
import com.example.demo.service.TodolistService;
import com.example.demo.service.UserService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(UrlConst.TODOLIST)
public class TodolistController {
	
	/** ToDoListService */
	private final UserService userService;
	private final TodolistService todolistService;
	
	/** CategoryService */
	private final CategoryService categoryService;
	
	/** Dozer Mapper */
	private final Mapper mapper;
	
	/** MessageSource */
	private final MessageSource messageSource;
	
	/** HttpSession session */
	private final HttpSession session;
	
	/** ModelMap */
	private final ModelMap modelMap;
	
	@GetMapping()
	public String view(@ModelAttribute("list") ArrayList<TodolistInfo> list ,@ModelAttribute("search") SearchForm form,@ModelAttribute("modelMap") ModelMap modelMap, SelectedIdForm selectedIdForm, @AuthenticationPrincipal User authUser, Model model) {
		
		session.removeAttribute(SessionKeyConst.SELECTED_TODOLIST_ID);
		
		if(modelMap != null && !modelMap.isEmpty()) {
			TodolistMessage message = (TodolistMessage)modelMap.get("message");
			storeMessage(model, message.getMessageId(), message.isError());
		}
		
		model.addAttribute("todolistColumn", TodolistColumn.class);
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
				List<TodolistInfo> todolists = todolistService.getUserTodolists(userInfo);
				model.addAttribute("todolists", todolists);
				model.addAttribute("search", false);
			}else {
				SearchOrder selectedOrder = SearchOrder.findSelectedOrder(form.getOrder());
				TodolistColumn selectedColumn = TodolistColumn.findSelectedColumn(form.getColumn());
				model.addAttribute("selectedOrder", selectedOrder);
				model.addAttribute("selectedColumn", selectedColumn);
				model.addAttribute("toDoLists", list);
				model.addAttribute("search", true);
			}
		}
		return ViewHtmlConst.TODOLIST;
	}
	
	@GetMapping("/new")
	public String view(Model model, TodolistNewForm form) {
		List<CategoryInfo> categories = categoryService.getCategories();
		model.addAttribute("categories", categories);
		return ViewHtmlConst.TODOLIST_NEW;
	}
	
	@PostMapping("/create")
	public String create(RedirectAttributes redirectAttributes, @AuthenticationPrincipal User authUser, Model model, @Validated TodolistNewForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			storeMessage(model, TodolistMessage.VALIDATE_FAILED.getMessageId(), TodolistMessage.VALIDATE_FAILED.isError());
			List<CategoryInfo> categories = categoryService.getCategories();
			model.addAttribute("categories", categories);
			return ViewHtmlConst.TODOLIST_NEW;
		}
		
		Optional<TodolistInfo> todolist = todolistService.createTodolist(form);
		
		TodolistMessage toDoListMessage = chooseMessage(todolist);
		modelMap.addAttribute("message", toDoListMessage);
		redirectAttributes.addFlashAttribute("modelMap", modelMap);
		
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
		List<TodolistInfo> todolists = todolistService.orderUserTodolists(form, user.get());
		redirectAttributes.addFlashAttribute("list", todolists);
		redirectAttributes.addFlashAttribute("search", form);
		return AppUtil.doRedirect(UrlConst.TODOLIST);
	}
	
	@GetMapping("/show")
	public String show(RedirectAttributes redirectAttributes, Model model, SelectedIdForm form) {
		String selectedTodolistId = (String)session.getAttribute(SessionKeyConst.SELECTED_TODOLIST_ID);
		if(selectedTodolistId==null) {
			return AppUtil.doRedirect(UrlConst.TODOLIST);
		}
		Optional<TodolistInfo> todolist = todolistService.getTodolist(selectedTodolistId);
		if(todolist.isEmpty()) {
			TodolistMessage showMessage = TodolistMessage.ID_NOT_FOUND;
			
			modelMap.addAttribute("message", showMessage);
			redirectAttributes.addFlashAttribute("modelMap", modelMap);
			return AppUtil.doRedirect(UrlConst.TODOLIST);
		}
		System.out.println(todolist.get().getListId());
		System.out.println(todolist.get().getCategory().getName());
		model.addAttribute("todolist" , todolist.get());

		return ViewHtmlConst.TODOLIST_SHOW;
	}
	
	/**
	 * 編集画面　一覧から取得したIDを基に作成します。IDがセッションに存在しない場合は一覧にリダイレクトします。
	 * 
	 * @param redirectAttributes エラー時のメッセージを一覧画面で表示末うため。
	 * @param model
	 * @param form 変更項目フォーム
	 * @return Todolist一覧画面
	 */
	@GetMapping("/edit")
	public String edit(RedirectAttributes redirectAttributes, Model model){
		
		String selectedTodolistId = (String)session.getAttribute(SessionKeyConst.SELECTED_TODOLIST_ID);
		if(selectedTodolistId==null) {
			return AppUtil.doRedirect(UrlConst.TODOLIST);
		}
		Optional<TodolistInfo> todolist = todolistService.getTodolist(selectedTodolistId);
		if(todolist.isEmpty()) {
			TodolistMessage editMessage = TodolistMessage.ID_NOT_FOUND;
			
			modelMap.addAttribute("message", editMessage);
			redirectAttributes.addFlashAttribute("modelMap", modelMap);
			
			return AppUtil.doRedirect(UrlConst.TODOLIST);
		}
		var selectedTodolist = mapper.map(todolist.get(), ToDoListEditDTO.class);
		selectedTodolist.setCategoryId(todolist.get().getCategory().getCategoryId());
		model.addAttribute("todolistForm" , selectedTodolist);
		
		List<CategoryInfo> categories = categoryService.getCategories();
		model.addAttribute("categories", categories);
		System.out.println(ListStatusKind.values());
		model.addAttribute("statusOptions", ListStatusKind.values());
		
		return ViewHtmlConst.TODOLIST_EDIT;
	}
	
	/**
	 * 変数editのパラメーターが存在する場合、セッションにID情報を保管し、編集画面へ遷移します。
	 * 
	 * @param form 選択されたIDを取得するフォーム
	 * @return 編集画面へリダイレクト
	 */
	@PostMapping(params="edit")
	public String edit(SelectedIdForm form) {
		session.setAttribute(SessionKeyConst.SELECTED_TODOLIST_ID, form.getSelectedTodolistId());
		return AppUtil.doRedirect(UrlConst.TODOLIST_EDIT);
	}
	
	@PostMapping(params="show")
	public String show(SelectedIdForm form) {
		session.setAttribute(SessionKeyConst.SELECTED_TODOLIST_ID, form.getSelectedTodolistId());
		return AppUtil.doRedirect(UrlConst.TODOLIST_SHOW);
	}
	
	@PostMapping("/update")
	public String update(RedirectAttributes redirectAttributes, Model model, @Validated TodolistEditForm form, BindingResult bindingResult, @AuthenticationPrincipal User user) {
		if(bindingResult.hasErrors()) {
			storeMessage(model, TodolistMessage.VALIDATE_FAILED.getMessageId(), TodolistMessage.VALIDATE_FAILED.isError());
			model.addAttribute("todolistForm", form);
			List<CategoryInfo> categories = categoryService.getCategories();
			model.addAttribute("categories", categories);
			model.addAttribute("statusOptions", ListStatusKind.values());
			return ViewHtmlConst.TODOLIST_EDIT_ERROR;
		}
		
		String selectedTodolistId = (String)session.getAttribute(SessionKeyConst.SELECTED_TODOLIST_ID);
		TodolistMessage updateMessage = todolistService.updateTodolist(form, selectedTodolistId);
		
		modelMap.addAttribute("message", updateMessage);
		
		redirectAttributes.addFlashAttribute("modelMap", modelMap);
		return AppUtil.doRedirect(UrlConst.TODOLIST);
	}
	
	@PostMapping(params="delete")
	public String delete(RedirectAttributes redirectAttributes, SelectedIdForm form) {
		TodolistMessage deleteMessage = todolistService.deleteTodolist(form.getSelectedTodolistId());
		modelMap.addAttribute("message", deleteMessage);
		redirectAttributes.addFlashAttribute("modelMap", modelMap);
		return AppUtil.doRedirect(UrlConst.TODOLIST);
	}
	
	private void storeMessage(Model model, String messageId, boolean isError) {
		String message = AppUtil.getMessage(messageSource, messageId);
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
	}
	
	private TodolistMessage chooseMessage(Optional<TodolistInfo> toDoList) {
		if(toDoList.isEmpty()){
			return TodolistMessage.FAILED;
		} else {
			return TodolistMessage.SUCCEED;
		}
	}
	
}
