package com.syslab.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.syslab.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	public User findByUsername(String username);
	public List<User> findAllByUsernameContainingOrderByUsernameAsc(String username);
	
}
