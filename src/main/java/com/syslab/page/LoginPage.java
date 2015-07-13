package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.service.LoginService;
import com.syslab.service.UserService;

public class LoginPage extends WebPage {
	
	@SpringBean
	private LoginService loginService;
	
	@SpringBean
	private UserService userService;
	
	public LoginPage() {
		
		Form form = new Form("form");
		this.add(form);
		
		final TextField<String> usernameField = new TextField<String>("username");
		usernameField.setDefaultModel(Model.of(new String()));
		usernameField.setRequired(true);
		form.add(usernameField);
		
		final PasswordTextField passwordField = new PasswordTextField("password");
		passwordField.setDefaultModel(Model.of(new String()));
		passwordField.setRequired(true);
		form.add(passwordField);
		
		AjaxButton submitButton = new AjaxButton("submit") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String username = usernameField.getModelObject();
				String password = passwordField.getModelObject();
				
				boolean loginSuccessful = loginService.login(username, password);
				
				if (loginSuccessful) {
					redirectToInterceptPage(new ImageAnalisisPage());					
				} else {
					error("Invalid username or password");
				}
				
			}
			
		};
		form.add(submitButton);
		
	}
	
}
