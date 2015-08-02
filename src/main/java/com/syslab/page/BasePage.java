package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mysql.jdbc.log.LogUtils;
import com.syslab.service.LoginService;

public abstract class BasePage extends WebPage {
	
	@SpringBean
	protected LoginService loginService;
	
	public BasePage() {
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		add(new Label("menu-username", username));
		
		AjaxLink<LoginPage> logoutButton = new AjaxLink<LoginPage>("logoutButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				loginService.logout();
				setResponsePage(LoginPage.class);
			}
		};
		
		add(logoutButton);
	}
	
}
