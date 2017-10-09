package com.tjaktor.shopping.service;

import com.tjaktor.shopping.entity.User;

public interface UserService {
	
	/**
	 * Find a user by username.
	 * 
	 * @param username String
	 * @return User
	 */
	public User findByUsername(String username);
}
