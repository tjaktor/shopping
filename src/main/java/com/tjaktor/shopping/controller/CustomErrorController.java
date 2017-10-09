package com.tjaktor.shopping.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";
	private static final String ERROR_TEMPLATE = "error";
	
	@RequestMapping(ERROR_PATH)
	public String error() {
		return ERROR_TEMPLATE;
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
