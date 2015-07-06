package com.syslab;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.syslab.page.HomePage;
import com.syslab.page.LoginPage;


public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends WebPage> getHomePage() {
		return LoginPage.class;
	}

	@Override
	public void init() {
		super.init();

		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		
		this.mountPage("LoginPage", LoginPage.class);
		this.mountPage("HomePage", HomePage.class);
		
	}
	
	
	
	
}
