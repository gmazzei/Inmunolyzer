package com.syslab.component;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class Noty {
	
	public void show(String message, AjaxRequestTarget target) {
		StringBuilder sb = new StringBuilder();
		sb.append("noty({ text: '" + message + "', modal: 'true', layout: 'center' });");
		target.appendJavaScript(sb.toString());
	}
	
}
