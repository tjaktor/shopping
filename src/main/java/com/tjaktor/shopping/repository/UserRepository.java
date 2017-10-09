package com.tjaktor.shopping.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjaktor.shopping.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	/**
	 * Find user by username.
	 * 
	 * @param username
	 * @return User
	 */
	public User findByUsername(String username);
}
