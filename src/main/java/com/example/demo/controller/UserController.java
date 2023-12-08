package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.constant.UrlConst;

@Controller
@RequestMapping(UrlConst.USER)
public class UserController {
	
	@GetMapping()
	public String view() {
		return "/user/mypage";
	}
}
