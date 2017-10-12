package com.tjaktor.shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public interface EventService {

	/**
	 * Subscribe to a subscriber list to get refresh events from the server.
	 * 
	 * @return SseEmitter
	 */
	public SseEmitter subscribe(SessionId sessionId);
	
}
