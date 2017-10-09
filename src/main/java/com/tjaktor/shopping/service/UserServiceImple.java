package com.tjaktor.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tjaktor.shopping.entity.User;
import com.tjaktor.shopping.entity.UserDetailsImple;
import com.tjaktor.shopping.repository.UserRepository;

@Service
public class UserServiceImple implements UserService, UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImple(UserRepository userRepository) {
		
		this.userRepository = userRepository;		
	}
	
	@Override
	public User findByUsername(String username) {
		
		return this.userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = findByUsername(username);
		
		if (null == user) {
			throw new UsernameNotFoundException(username);
		}
		
		return new UserDetailsImple(user);
	}
}
