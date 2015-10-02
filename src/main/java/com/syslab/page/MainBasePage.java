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

public abstract class MainBasePage extends WebPage {
	
	@SpringBean
	protected LoginService loginService;
	
	protected User loggedUser;
	
	public MainBasePage() {
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
		BookmarkablePageLink<ImageAnalisisPage> imageAnalisis = new BookmarkablePageLink<ImageAnalisisPage>("sidebar-image-analisis", ImageAnalisisPage.class);
		BookmarkablePageLink<DiagnosisListPage> diagnosisList = new BookmarkablePageLink<DiagnosisListPage>("sidebar-diagnosis", DiagnosisListPage.class);
		BookmarkablePageLink<PatientListPage> patientList = new BookmarkablePageLink<PatientListPage>("sidebar-patient", PatientListPage.class);
		BookmarkablePageLink<UserListPage> usersList = new BookmarkablePageLink<UserListPage>("sidebar-users", UserListPage.class);
		BookmarkablePageLink<StatisticsPage> statistics = new BookmarkablePageLink<StatisticsPage>("sidebar-statistics", StatisticsPage.class);
		
		boolean hasPrivilege = loggedUser.isAdmin();
		usersList.setVisible(hasPrivilege);
		
		add(imageAnalisis);
		add(diagnosisList);
		add(patientList);
		add(usersList);
		add(statistics);
	}
	
	protected User loadLoggedUser() {
		this.loggedUser = loginService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		return loggedUser;
	}
}
