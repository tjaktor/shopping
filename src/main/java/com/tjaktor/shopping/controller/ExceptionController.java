package com.tjaktor.shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.tjaktor.shopping.exception.CartServiceException;
import com.tjaktor.shopping.exception.NotFoundException;

@ControllerAdvice("com.tjaktor.shopping.controller")
public class ExceptionController {

	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	private final static String DEFAULT_ERROR_VIEW = "error/default404";
	
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(NotFoundException.class)
	protected ModelAndView notFound(Exception ex) {
		
		logger.warn("Not found error: " + ex.getMessage() + ". " + ex);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Throwable.class, CartServiceException.class, NumberFormatException.class})
	protected ModelAndView throwableException(Throwable ex) {
		
		logger.warn("Internal error: " + ex.getMessage() + ". " + ex);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.setViewName("error/default500");
		return mav;
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationException.class)
	protected ModelAndView notAuthorized(Exception ex) {
		
		logger.warn("Authentication error: " + ex.getMessage() + ". " + ex);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
	
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler({SecurityException.class, AccessDeniedException.class})
	protected ModelAndView securityException(Exception ex) {
		
		logger.warn("Security error: " + ex.getMessage() + ". " + ex);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
}
