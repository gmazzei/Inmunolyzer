package com.syslab.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.service.LoginService;

public class HomePage extends WebPage {
	
	@SpringBean
	private LoginService loginService;
	
	public HomePage() {
		
		add(new Label("message", loginService.toString()));
		
    }
}
