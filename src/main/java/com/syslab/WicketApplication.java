package com.syslab;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.syslab.page.BasePage;
import com.syslab.page.CreateUserPage;
import com.syslab.page.ImageAnalisisPage;
import com.syslab.page.LoginPage;
import com.syslab.page.ShowUserPage;
import com.syslab.page.UserListPage;
import com.syslab.security.SecureWebSession;


public class WicketApplication extends AuthenticatedWebApplication {

	@Override
	public Class<? extends WebPage> getHomePage() {
		return LoginPage.class;
	}

	@Override
	public void init() {
		super.init();
		
		getDebugSettings().setAjaxDebugModeEnabled(false); 
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		
		mountPage("LoginPage", LoginPage.class);
		mountPage("BasePage", BasePage.class);
		mountPage("ImageAnalisisPage", ImageAnalisisPage.class);
		mountPage("UserListPage", UserListPage.class);
		mountPage("ShowUserPage", ShowUserPage.class);
		mountPage("CreateUserPage", CreateUserPage.class);
		
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return SecureWebSession.class;
	}	
	
}
