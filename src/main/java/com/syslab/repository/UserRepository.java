package com.syslab.repository;

import org.springframework.data.repository.CrudRepository;

import com.syslab.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
	
}
