package com.syslab;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.syslab.page.BasePage;
import com.syslab.page.CreateDiagnosisPage;
import com.syslab.page.CreateUserPage;
import com.syslab.page.DiagnosisListPage;
import com.syslab.page.ErrorPage;
import com.syslab.page.ImageAnalisisPage;
import com.syslab.page.ImageDetailsPage;
import com.syslab.page.LoginPage;
import com.syslab.page.MainPage;
import com.syslab.page.ShowDiagnosisPage;
import com.syslab.page.ShowUserPage;
import com.syslab.page.StatisticsPage;
import com.syslab.page.UpdateDiagnosisPage;
import com.syslab.page.UpdatePasswordPage;
import com.syslab.page.UpdateUserPage;
import com.syslab.page.UserListPage;
import com.syslab.security.SecureWebSession;


public class WicketApplication extends AuthenticatedWebApplication {

	@Override
	public Class<? extends WebPage> getHomePage() {
		return MainPage.class;
	}

	@Override
	public void init() {
		super.init();
		
		//Encrypt URLs		
		getDebugSettings().setAjaxDebugModeEnabled(false); 
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getApplicationSettings().setInternalErrorPage(ErrorPage.class);
		
		
		mountPage("MainPage", MainPage.class);
		mountPage("LoginPage", LoginPage.class);
		mountPage("BasePage", BasePage.class);
		mountPage("ImageAnalisisPage", ImageAnalisisPage.class);
		mountPage("ImageDetailsPage", ImageDetailsPage.class);
		mountPage("DiagnosisListPage", DiagnosisListPage.class);
		mountPage("ShowDiagnosisPage", ShowDiagnosisPage.class);
		mountPage("CreateDiagnosisPage", CreateDiagnosisPage.class);
		mountPage("UpdateDiagnosisPage", UpdateDiagnosisPage.class);
		mountPage("UserListPage", UserListPage.class);
		mountPage("ShowUserPage", ShowUserPage.class);
		mountPage("CreateUserPage", CreateUserPage.class);
		mountPage("UpdateUserPage", UpdateUserPage.class);
		mountPage("UpdatePasswordPage", UpdatePasswordPage.class);
		mountPage("StatisticsPage", StatisticsPage.class);
		mountPage("ErrorPage", ErrorPage.class);
		
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
