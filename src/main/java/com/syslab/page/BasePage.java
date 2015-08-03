package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.context.SecurityContextHolder;

import com.syslab.entity.User;
import com.syslab.service.LoginService;

public abstract class BasePage extends WebPage {
	
	@SpringBean
	protected LoginService loginService;
	
	protected User loggedUser;
	
	public BasePage() {
		loadLoggedUser();
		buildUserMenu();
		buildSideBar();
	}
	
	
	protected void buildUserMenu() {
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
	
	protected void buildSideBar() {
		add(new BookmarkablePageLink<ImageAnalisisPage>("sidebar-image-analisis", ImageAnalisisPage.class));
		add(new BookmarkablePageLink<DiagnosisListPage>("sidebar-diagnosis", DiagnosisListPage.class));
		add(new BookmarkablePageLink<UserListPage>("sidebar-users", UserListPage.class));
		add(new BookmarkablePageLink<StatisticsPage>("sidebar-statistics", StatisticsPage.class));
	}
	
	protected User loadLoggedUser() {
		this.loggedUser = loginService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		return loggedUser;
	}
}
