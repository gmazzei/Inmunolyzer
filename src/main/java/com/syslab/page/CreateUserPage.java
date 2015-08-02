package com.syslab.page;

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

import com.syslab.entity.User;
import com.syslab.service.UserService;

public class CreateUserPage extends BasePage {
	

	@SpringBean
	private UserService userService;
	
	public CreateUserPage() {
		User user = new User();
		this.preparePage(user);
	}
	
	public CreateUserPage(PageParameters params) {
		Integer id = params.get("entityId").toInteger();
		User user = userService.getUser(id);
		this.preparePage(user);
	}
	
	private void preparePage(User user) {
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		Form<User> form = new Form<User>("form", new CompoundPropertyModel<User>(Model.of(user)));
		add(form);
		
		RequiredTextField<String> username = new RequiredTextField<String>("username");
		username.setLabel(Model.of("Username"));
		form.add(username);
		
		PasswordTextField password = new PasswordTextField("password");
		password.setLabel(Model.of("Password"));
		password.setRequired(true);
		form.add(password);
		
		PasswordTextField passwordConfirmation = new PasswordTextField("passwordConfirmation", Model.of(new String()));
		passwordConfirmation.setLabel(Model.of("Confirm password"));
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
				setResponsePage(ShowUserPage.class, params);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
			
		};
		
		form.add(submitButton);
		
	}
	
}
