package com.tjaktor.shopping.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

/**
 * Used for event notification to the application clients.
 * 
 * Under construction. TEST VERSION.
 */
@Service("Events")
public class EventServiceImple implements EventService, EventPropagation {
		
	Map<String, SseEmitter> emitters = new HashMap<>();
		
	/**
	 * Subscribe a service side event -emitter to the emitters list.
	 * If the list already countains an emitter from the same session, the old one will be replaced.
	 * 
	 * @param sessionId {@link SessionId}
	 * @return Emitter
	 */
	@Override
	public SseEmitter subscribe(SessionId sessionId) {

		final SseEmitter emitter = new SseEmitter();
		
		emitter.onCompletion(() -> {
			this.emitters.remove(emitter);
		});
		
		emitter.onTimeout(() -> {
			emitter.complete();
			this.emitters.remove(emitter);
		});
		
		this.emitters.put(sessionId.getSessionId(), emitter);
		return emitter;
	}


	/**
	 * Notify all the emitters for an event.
	 */
	@Override
	public void propagateEvent() {

		emitters.forEach((key, emitter) -> {
			if (emitter != null) {
				try {
					SseEventBuilder builder = SseEmitter.event().name("message").data("ping");
					emitter.send(builder);
				} catch (IOException ex) {
					emitter.completeWithError(ex);
				}
			}
		});
	}
}
