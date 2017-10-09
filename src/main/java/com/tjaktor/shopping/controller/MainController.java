package com.tjaktor.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	/**
	 * The Index -page. Under construction.
	 * @return redirect to the carts -page
	 */
	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/carts/";
	}	
}
