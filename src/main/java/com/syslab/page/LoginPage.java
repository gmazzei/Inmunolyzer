package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.service.LoginService;

public class LoginPage extends LoginBasePage {
	
	@SpringBean
	private LoginService loginService;
	
	public LoginPage() {
		
		Form form = new Form("form");
		this.add(form);
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		form.add(feedbackPanel);
		
		final TextField<String> usernameField = new TextField<String>("username");
		usernameField.setDefaultModel(Model.of(new String()));
		usernameField.setRequired(true);
		usernameField.setLabel(Model.of("Username"));
		form.add(usernameField);
		
		final PasswordTextField passwordField = new PasswordTextField("password");
		passwordField.setDefaultModel(Model.of(new String()));
		passwordField.setRequired(true);
		passwordField.setLabel(Model.of("Password"));
		form.add(passwordField);
		
		AjaxButton submitButton = new AjaxButton("submit") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String username = usernameField.getModelObject();
				String password = passwordField.getModelObject();
				
				boolean loginSuccessful = loginService.login(username, password);						
				if (loginSuccessful) {
					setResponsePage(ImageAnalisysPage.class);					
				} else {
					error("Invalid Username or Password");
					target.add(feedbackPanel);
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
			
		};
		
		form.add(new BookmarkablePageLink<SignUpPage>("signUpButton", SignUpPage.class));
		
		form.add(submitButton);
	}
	
}
