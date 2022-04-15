package com.validation.controller;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.validation.entites.LoginData;

@Controller
public class MyController {

	@GetMapping("/form")
	public String openForm(Model model) {
		
		System.out.println("opening Form");
		model.addAttribute("loginData",new LoginData());
		return "form";
	}
	
	// handler for processing the form
	@PostMapping("/process")
	public String processForm(@Valid @ModelAttribute("loginData") LoginData loginData, BindingResult result)
	{
		if(result.hasErrors())
		{
			System.out.println(result);
		}
		
		System.out.println(loginData);
		return "success";
	}
	
	
}
