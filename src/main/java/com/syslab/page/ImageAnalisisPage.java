package com.syslab.page;

import java.io.FileOutputStream;
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
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.File;

import com.google.gson.Gson;
import com.syslab.component.Noty;
import com.syslab.entity.Diagnosis;
import com.syslab.entity.Technique;
import com.syslab.imageAnalisis.ImageAnalizer;

public class ImageAnalisisPage extends MainBasePage {
		
	@SpringBean
	private ImageAnalizer imageAnalizer;
	
	public ImageAnalisisPage() {
		
		Form<Diagnosis> form = new Form<Diagnosis>("form", new CompoundPropertyModel<Diagnosis>(new Diagnosis()));
		add(form);
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		form.add(feedbackPanel);
		
		IModel<List<Technique>> techniqueModel = new LoadableDetachableModel<List<Technique>>() {

			@Override
			protected List<Technique> load() {
				return Arrays.asList(Technique.values());
			}
		};
		
		final DropDownChoice<Technique> techniques = new DropDownChoice<Technique>("technique", techniqueModel);
		techniques.setRequired(true);
		techniques.setLabel(Model.of("Technique"));
		form.add(techniques);

		
		IModel<List<FileUpload>> fileUploadModel = new LoadableDetachableModel<List<FileUpload>>() {
			@Override
			protected List<FileUpload> load() {
				return new ArrayList<FileUpload>();
			}
		};
		
		final FileUploadField fileUploader = new FileUploadField("image", fileUploadModel);
		fileUploader.setRequired(true);
		fileUploader.setLabel(Model.of("Image"));
		form.add(fileUploader);
		
		
		AjaxButton ajaxButton = new AjaxButton("submit") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Diagnosis diagnosis = (Diagnosis) form.getModelObject();				
				FileUpload fileUpload = fileUploader.getFileUpload();
				
				String originalPath = fileUpload.getClientFileName();
				String transformedPath = "transformed-" + fileUpload.getClientFileName();

				try {
					FileOutputStream fos = new FileOutputStream(originalPath);
					byte[] fileBytes = fileUpload.getBytes();
					fos.write(fileBytes);
					fos.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
				Double result = imageAnalizer.analize(originalPath, transformedPath);
				diagnosis.setResult(result);
				
				PageParameters params = new PageParameters();
				String entity = new Gson().toJson(diagnosis);
				params.add("entity", entity);
				params.add("originalPath", originalPath);
				params.add("transformedPath", transformedPath);
				setResponsePage(new ShowImagePage(params));
			}
			
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				List<String> messages = new ArrayList<String>();
				
				if (!techniques.getFeedbackMessages().isEmpty())
					messages.add(techniques.getFeedbackMessages().first().getMessage().toString());
				
				if (!fileUploader.getFeedbackMessages().isEmpty())
					messages.add(fileUploader.getFeedbackMessages().first().getMessage().toString());
				
				new Noty().show(messages, target);
			}
		};
		
		
		form.add(ajaxButton);
		
    }
	
}