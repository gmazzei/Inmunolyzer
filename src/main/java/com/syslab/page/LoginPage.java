package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.service.LoginService;

public class LoginPage extends WebPage {
	
	@SpringBean
	private LoginService loginService;
	
	public LoginPage() {
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
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
					setResponsePage(ImageAnalisisPage.class);					
				} else {
					error("Invalid username or password");
					target.add(feedbackPanel);
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
			
		};
		form.add(submitButton);
		
	}
	
}
