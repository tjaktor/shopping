package com.tjaktor.shopping.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.tjaktor.shopping.service.EventPropagation;
import com.tjaktor.shopping.service.EventService;
import com.tjaktor.shopping.service.SessionId;

@SessionAttributes("sessionId")
@RestController
public class EventController {
		
	@Autowired
	@Qualifier("Events")
	private EventService eventService;	

	@Autowired
	@Qualifier("Events")
	private EventPropagation eventPropagation;
	
	@RequestMapping(value = "/event")
	public SseEmitter event(@ModelAttribute("sessionId") SessionId sessionId) {
		
		SseEmitter emitter = this.eventService.subscribe(sessionId);
		return emitter;
	}
	
	@ModelAttribute("sessionId")
	public SessionId getSessionId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return new SessionId(session.getId());
	}
}