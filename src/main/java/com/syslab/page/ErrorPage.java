package com.syslab.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class ErrorPage extends WebPage {
	
	public ErrorPage() {
		add(new Label("message", "Ups, an error just ocurred. Please contact SysLab for support."));
	}
	
}
