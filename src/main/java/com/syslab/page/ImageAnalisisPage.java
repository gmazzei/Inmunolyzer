package com.syslab.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.gson.Gson;
import com.syslab.entity.Diagnosis;
import com.syslab.entity.Technique;
import com.syslab.imageAnalisis.ImageAnalizer;

public class ImageAnalisisPage extends MainBasePage {
		
	@SpringBean
	private ImageAnalizer imageAnalizer;
	
	public ImageAnalisisPage() {
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		Form<Diagnosis> form = new Form<Diagnosis>("form", new CompoundPropertyModel<Diagnosis>(new Diagnosis()));
		add(form);
		
		IModel<List<Technique>> techniqueModel = new LoadableDetachableModel<List<Technique>>() {

			@Override
			protected List<Technique> load() {
				return Arrays.asList(Technique.values());
			}
		};
		
		DropDownChoice<Technique> techniques = new DropDownChoice<Technique>("technique", techniqueModel);
		techniques.setRequired(true);
		form.add(techniques);

		
		IModel<List<FileUpload>> fileUploadModel = new LoadableDetachableModel<List<FileUpload>>() {
			@Override
			protected List<FileUpload> load() {
				return new ArrayList<FileUpload>();
			}
		};
		
		final FileUploadField fileUploader = new FileUploadField("image", fileUploadModel);
		fileUploader.setRequired(true);
		form.add(fileUploader);
		
		
		AjaxButton ajaxButton = new AjaxButton("submit") {
						
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Diagnosis diagnosis = (Diagnosis) form.getModelObject();
				
				FileUpload fileUpload = fileUploader.getFileUpload();
				byte[] fileBytes = fileUpload.getBytes();
				Double result = imageAnalizer.analize(fileBytes);
				diagnosis.setResult(result);
				
				PageParameters params = new PageParameters();
				String entity = new Gson().toJson(diagnosis);
				params.add("entity", entity);
				setResponsePage(new ImageDetailsPage(params));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		};
		
		form.add(ajaxButton);
		
    }
	
}