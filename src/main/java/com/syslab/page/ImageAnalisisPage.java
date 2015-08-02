package com.syslab.page;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.entity.Technique;
import com.syslab.imageAnalisis.ImageAnalizer;

public class ImageAnalisisPage extends BasePage {
		
	@SpringBean
	private ImageAnalizer imageAnalizer;
	
	public ImageAnalisisPage() {
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		Form form = new Form("form");
		add(form);
		
		IModel<List<Technique>> techniqueModel = new LoadableDetachableModel<List<Technique>>() {

			@Override
			protected List<Technique> load() {
				return Arrays.asList(Technique.values());
			}
		};
		
		DropDownChoice<Technique> techniques = new DropDownChoice<Technique>("technique", techniqueModel);
		techniques.setDefaultModel(techniqueModel);
		techniques.setRequired(true);
		form.add(techniques);
		
		final FileUploadField fileUploader = new FileUploadField("fileUploader");
		fileUploader.setOutputMarkupId(true);
		fileUploader.setRequired(true);
		form.add(fileUploader);
		
		final Label resultValueField = new Label("resultValue");
		resultValueField.setDefaultModel(Model.of(new String()));
		resultValueField.setOutputMarkupId(true);
		add(resultValueField);
		
		
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

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		
		form.add(ajaxButton);
		
    }
	
}