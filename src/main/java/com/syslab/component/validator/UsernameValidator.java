package com.syslab.component.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import com.syslab.entity.User;
import com.syslab.service.UserService;

public class UsernameValidator implements IValidator<String> {
	
	private UserService userService;
	
	public UsernameValidator(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public void validate(IValidatable<String> validatable) {
		final String username = validatable.getValue();
		System.out.println("USER SERVICE = " + userService);
		User user = userService.findByUsername(username);
		
		if (user != null) {
			error(validatable, "Username already exists.");
		}
		
	}
	
	private void error(IValidatable<String> validatable, String message) {
		ValidationError error = new ValidationError(message);
		validatable.error(error);
	}
	
	
}
