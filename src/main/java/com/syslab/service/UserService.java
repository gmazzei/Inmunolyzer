package com.syslab.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syslab.entity.User;
import com.syslab.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Collection<User> getAll() {
		return (Collection<User>) userRepository.findAll();
	}
	
}
