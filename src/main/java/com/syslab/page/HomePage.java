package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.service.UserService;

public class HomePage extends WebPage {
	
	@SpringBean
	private UserService userService;
	
	public HomePage() {
		
		Form form = new Form("form");
		add(form);
		
		final FileUploadField fileUploader = new FileUploadField("fileUploader");
		fileUploader.setOutputMarkupId(true);
		form.add(fileUploader);
		
		AjaxButton ajaxButton = new AjaxButton("submit", Model.of("Analizar")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				FileUpload fileUpload = fileUploader.getFileUpload();
				byte[] fileBytes = fileUpload.getBytes();
				
				//TODO: Analize image
				
				super.onSubmit(target, form);
			}
			
		};
		form.add(ajaxButton);
    }
	
}
