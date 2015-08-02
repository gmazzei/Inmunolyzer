package com.syslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syslab.entity.User;
import com.syslab.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAll() {
		return (List<User>) userRepository.findAll();
	}
	
	public User getUser(Integer id) {
		return userRepository.findOne(id);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
	
	public List<User> findAllByUsernameContaining(String username) {
		return userRepository.findAllByUsernameContainingOrderByUsernameAsc(username);
	}
	
}
