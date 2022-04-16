package com.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeControlller {
	
	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title","Home - Smart Contact Manager");
		return "home";
	}
	
	
	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","about - Smart Contact Manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title","Register - Smart Contact Manager");
		return "signup";
	}

	
	
}
