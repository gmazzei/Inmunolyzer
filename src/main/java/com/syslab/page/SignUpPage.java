package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.component.validator.UsernameValidator;
import com.syslab.entity.User;
import com.syslab.service.LoginService;
import com.syslab.service.UserService;

public class SignUpPage extends LoginBasePage {
	
	@SpringBean 
	private LoginService loginService;

	@SpringBean
	private UserService userService;
	
	public SignUpPage() {
		User user = new User();
		this.preparePage(user);
	}
	
	
	private void preparePage(User user) {
		
		Form<User> form = new Form<User>("form", new CompoundPropertyModel<User>(Model.of(user)));
		add(form);
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		form.add(feedbackPanel);
		
		RequiredTextField<String> username = new RequiredTextField<String>("username");
		username.setLabel(Model.of("Username"));
		username.add(new UsernameValidator(userService));
		form.add(username);
		
		PasswordTextField password = new PasswordTextField("password");
		password.setLabel(Model.of("Password"));
		password.setRequired(true);
		form.add(password);
		
		PasswordTextField passwordConfirmation = new PasswordTextField("passwordConfirmation", Model.of(new String()));
		passwordConfirmation.setLabel(Model.of("Repeat password"));
		password.setRequired(true);
		form.add(passwordConfirmation);
		
		form.add(new EqualPasswordInputValidator(password, passwordConfirmation));
		
		AjaxButton submitButton = new AjaxButton("submitButton", Model.of("Confirm")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				User user = (User) form.getModelObject();
				userService.save(user);
				loginService.login(user.getUsername(), user.getPassword());
				setResponsePage(ImageAnalisisPage.class);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
			
		};
		
		form.add(new BookmarkablePageLink<LoginPage>("returnButton", LoginPage.class));
		
		form.add(submitButton);
		
	}
	
}
