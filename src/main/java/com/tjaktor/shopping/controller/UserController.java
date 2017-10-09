package com.tjaktor.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin/")
public class UserController {

	@RequestMapping(value = "users", method = RequestMethod.GET)
	public String userList(Model model) {
		
		return "admin/users";
		
	}
	
	@RequestMapping(value = "registeruser", method = RequestMethod.GET)
	public String registerUser(Model model) {
		
		if (! model.containsAttribute("userDto")) {
			
			// model.addAttribute("userDto", new UserDto() );
		}
		
		return "admin/registeruser";
		
	}
	
	public String validateNewUser() {
		
		
		
		return "redirect:/admin/registeruser";
		
	}
	
}
