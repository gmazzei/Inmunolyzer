package com.syslab.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class MainPage extends WebPage {

	public MainPage() {
		add(new BookmarkablePageLink<LoginPage>("loginButton", LoginPage.class));
		add(new BookmarkablePageLink<SignUpPage>("signUpButton", SignUpPage.class));
	}
	
}
