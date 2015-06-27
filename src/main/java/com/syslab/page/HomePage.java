package com.syslab.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.service.UserService;

public class HomePage extends WebPage {
	
	@SpringBean
	private UserService userService;
	
	public HomePage() {
		add(new Label("message", userService.getAll().size()));
    }
	
}
