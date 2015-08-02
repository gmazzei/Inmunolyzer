package com.syslab.page;

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
		User user = userService.getUser(id);
		
		add(new Label("id", user.getId()));
		add(new Label("username", user.getUsername()));
		add(new BookmarkablePageLink<UserListPage>("returnButton", UserListPage.class));
	}

}
