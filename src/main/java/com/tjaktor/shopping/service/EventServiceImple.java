package com.tjaktor.shopping.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

/**
 * Used for event notification to application clients.
 * 
 * Under construction. TEST VERSION.
 */
@Service("Events")
public class EventServiceImple implements EventService, EventPropagation {
	
	Set<SseEmitter> emitters = new HashSet<>();
	
	/**
	 * Subscribe a new emitter.
	 * 
	 * @return Emitter
	 */
	@Override
	public SseEmitter subscribe() {

		final SseEmitter emitter = new SseEmitter();
		
		emitter.onCompletion(() -> {
			this.emitters.remove(emitter);
		});
		
		emitter.onTimeout(() -> {
			emitter.complete();
			this.emitters.remove(emitter);
		});
		
		this.emitters.add(emitter);
		return emitter;
		
	}


	/**
	 * Notify emitters for an event.
	 */
	@Override
	public void propagateEvent() {

		emitters.forEach(emitter -> {
			if (null != emitter) {
				try {
					SseEventBuilder builder = SseEmitter.event().name("message").data("ping");				
					emitter.send(builder);
				} catch ( IOException ex ) {
					emitter.completeWithError(ex);
				}
			}
		});
	}
}
