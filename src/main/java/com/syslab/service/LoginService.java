package com.syslab.service;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syslab.entity.User;
import com.syslab.repository.UserRepository;

@Service
public class LoginService  {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean login(String username, String password) {
		AuthenticatedWebSession session = AuthenticatedWebSession.get();
		
		if (session.signIn(username, password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void logout() {
		Session.get().invalidate();
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
