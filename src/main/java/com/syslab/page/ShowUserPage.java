package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.entity.User;
import com.syslab.service.UserService;

public class ShowUserPage extends BasePage {

	@SpringBean
	private UserService userService;
	
	public ShowUserPage(PageParameters params) {
		
		Integer id = params.get("entityId").toInt();
		final User user = userService.getUser(id);
		
		add(new Label("id", user.getId()));
		add(new Label("username", user.getUsername()));
		
		AjaxLink<Void> editButton = new AjaxLink<Void>("editButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				PageParameters params = new PageParameters();
				params.add("entityId", user.getId());
				setResponsePage(CreateUserPage.class, params);
			}
		};
		
		AjaxLink<Void> deleteButton = new AjaxLink<Void>("deleteButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				userService.delete(user);
				setResponsePage(UserListPage.class);
			}
		};
		
		add(editButton);
		add(deleteButton);
		add(new BookmarkablePageLink<UserListPage>("returnButton", UserListPage.class));
	}

}
