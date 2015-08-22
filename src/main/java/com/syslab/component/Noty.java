package com.syslab.component;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;

public class Noty {
	
	public void show(String message, AjaxRequestTarget target) {
		StringBuilder sb = new StringBuilder();
		sb.append("noty({ text: '<label><strong>");
		sb.append(message);
		sb.append("</strong></label>', type: 'warning', modal: 'true', layout: 'center' });");
		target.appendJavaScript(sb.toString());
	}
	
	public void show(List<String> messages, AjaxRequestTarget target) {
		StringBuilder sb = new StringBuilder();
		sb.append("noty({ text: \"<ul>");
		
		for (String message : messages) {
			sb.append("<li><label><strong>");
			sb.append(message);
			sb.append("</strong></label></li>");
		}
		
		sb.append("</ul>\", type: 'warning', modal: 'true', layout: 'center', closeWith: ['click', 'backdrop'] });");
		target.appendJavaScript(sb.toString());
	}
}
