package com.practical.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {

	@RequestMapping(value = "/about",method = RequestMethod.GET)
	public String about(Model model)
	{
		System.out.println("Inside about handler ...");
		
		//putting data in model
		model.addAttribute("name","Prince Singh"); 
		
		model.addAttribute("currentDate",new Date().toLocaleString());
		return "about";
		//about.html
		
		
		
	}
	
	//this is for handling iterations
	
	@GetMapping("/example-loop")
	public String iterateHandler(Model m) {
		// send
		
		// create a list of elemets
		
		List<String> names = List.of("Ankit","Laxmi","karan","john");
		
		m.addAttribute("names",names);
		
		
		
		
		
		return "iterate";
	}
	
	
	// handler for conditional statments
	
	@GetMapping("/condition")
	public String conditionHandler(Model m) {
		
		System.out.println("conditional handler executed..");
		m.addAttribute("isActive", false);
		m.addAttribute("gender", "F");
		
		List<Integer> list = List.of(1);
		
		m.addAttribute("mylist",list);
		
		return "condition";
	}
	
	
	// handler for including fragments
	@GetMapping("/service")
	public String servicesHandler(Model m) {
		
		m.addAttribute("title","I like to eat samosa");
		m.addAttribute("subtitle",LocalDateTime.now().toString());
		
		return "service";
	}
	
	//for new about
	@GetMapping("/aboutnew")
	public String newAbout()
	{
		return "aboutnew";
	}
	
	
	//for new contact
		@GetMapping("/contact")
		public String contact()
		{
			return "contact";
		}
	
	
	
}
