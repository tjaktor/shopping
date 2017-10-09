package com.tjaktor.shopping.service;

import org.springframework.stereotype.Service;

@Service
public interface EventPropagation {

	/**
	 * Notify subscribers about event.
	 */
	public void propagateEvent();
	
}