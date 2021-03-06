package com.syslab.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.component.Noty;
import com.syslab.component.validator.UsernameValidator;
import com.syslab.entity.User;
import com.syslab.service.UserService;

public class CreateUserPage extends MainBasePage {
	

	@SpringBean
	private UserService userService;
	
	public CreateUserPage() {
		User user = new User();
		this.preparePage(user);
	}
	
	
	private void preparePage(User user) {
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		Form<User> form = new Form<User>("form", new CompoundPropertyModel<User>(Model.of(user)));
		add(form);
		
		final RequiredTextField<String> username = new RequiredTextField<String>("username");
		username.setLabel(Model.of("Username"));
		username.add(new UsernameValidator(userService));
		form.add(username);
		
		final PasswordTextField password = new PasswordTextField("password");
		password.setLabel(Model.of("Password"));
		password.setRequired(true);
		form.add(password);
		
		final PasswordTextField passwordConfirmation = new PasswordTextField("passwordConfirmation", Model.of(new String()));
		passwordConfirmation.setLabel(Model.of("Repeat Password"));
		password.setRequired(true);
		form.add(passwordConfirmation);
		
		form.add(new EqualPasswordInputValidator(password, passwordConfirmation));
		
		AjaxButton submitButton = new AjaxButton("submitButton", Model.of("Create User")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				User user = (User) form.getModelObject();
				userService.save(user);
				PageParameters params = new PageParameters();
				params.add("entityId", user.getId());
				setResponsePage(new ShowUserPage(params));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				List<String> messages = new ArrayList<String>();
				
				if (!username.getFeedbackMessages().isEmpty())
					messages.add(username.getFeedbackMessages().first().getMessage().toString());
				
				if (!password.getFeedbackMessages().isEmpty())
					messages.add(password.getFeedbackMessages().first().getMessage().toString());
				
				if (!passwordConfirmation.getFeedbackMessages().isEmpty())
					messages.add(passwordConfirmation.getFeedbackMessages().first().getMessage().toString());
				
				if (!form.getFeedbackMessages().isEmpty())
					messages.add(form.getFeedbackMessages().first().getMessage().toString());
				
				new Noty().show(messages, target);
			}
			
		};
		
		form.add(submitButton);
		
	}
	
}
