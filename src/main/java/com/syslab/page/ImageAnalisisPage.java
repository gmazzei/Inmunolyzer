package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.imageAnalisis.ImageAnalizer;

public class ImageAnalisisPage extends BasePage {
		
	@SpringBean
	private ImageAnalizer imageAnalizer;
	
	public ImageAnalisisPage() {
		
		Form form = new Form("form");
		this.add(form);
		
		final FileUploadField fileUploader = new FileUploadField("fileUploader");
		fileUploader.setOutputMarkupId(true);
		form.add(fileUploader);
		
		final Label resultValueField = new Label("resultValue");
		resultValueField.setDefaultModel(Model.of(new String()));
		resultValueField.setOutputMarkupId(true);
		this.add(resultValueField);
		
		
		AjaxButton ajaxButton = new AjaxButton("submit") {
						
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				FileUpload fileUpload = fileUploader.getFileUpload();
				byte[] fileBytes = fileUpload.getBytes();
				Double result = imageAnalizer.analize(fileBytes);

				resultValueField.setDefaultModelObject(result.toString());
				target.add(resultValueField);
				
				super.onSubmit(target, form);
			}
			
		};
		
		form.add(ajaxButton);
		
    }
	
}