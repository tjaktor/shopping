package com.tjaktor.shopping.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tjaktor.shopping.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
	/**
	 * Find a role by it's name.
	 * 
	 * @param name
	 * @return Role
	 */
	public Role findByName(String name);
}
