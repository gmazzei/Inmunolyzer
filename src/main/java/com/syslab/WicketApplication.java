package com.syslab;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.syslab.page.BasePage;
import com.syslab.page.ImageAnalisisPage;
import com.syslab.page.LoginPage;
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
		
		this.mountPage("LoginPage", LoginPage.class);
		this.mountPage("BasePage", BasePage.class);
		this.mountPage("ImageAnalisisPage", ImageAnalisisPage.class);
		
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
