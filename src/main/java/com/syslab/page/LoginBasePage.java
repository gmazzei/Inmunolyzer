package com.syslab.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.service.LoginService;

public abstract class LoginBasePage extends WebPage {

	@SpringBean
	private LoginService loginService;
	
	public LoginBasePage() {
		super();
		controlAccess();
	}
	
	private void controlAccess() {
		if (loginService.isUserLogged()) {
			setResponsePage(ImageAnalisysPage.class);
		}
	}
	
}
