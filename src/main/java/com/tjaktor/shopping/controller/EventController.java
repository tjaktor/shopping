package com.tjaktor.shopping.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.tjaktor.shopping.service.EventPropagation;
import com.tjaktor.shopping.service.EventService;

@SessionAttributes("eventsourcing")
@RestController
public class EventController {
	
	@Autowired
	@Qualifier("Events")
	private EventService eventService;	

	@Autowired
	@Qualifier("Events")
	private EventPropagation eventPropagation;
	
	@RequestMapping(value = "/event", produces = "text/event-stream")
	public SseEmitter event() throws IOException {
		
		SseEmitter emitter = this.eventService.subscribe();
		
		return emitter;
		
	}
	
}
