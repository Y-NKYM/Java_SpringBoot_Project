package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.constant.UrlConst;

@Controller
@RequestMapping(UrlConst.HOME)
public class HomeController {
	
	@GetMapping()
	public String view() {
		return "/home/index";
	}
}
