package com.syslab.service;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.springframework.stereotype.Service;

@Service
public class LoginService  {
	
	
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
	
}
