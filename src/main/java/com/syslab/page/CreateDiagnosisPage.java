package com.syslab.page;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
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
import com.syslab.service.DiagnosisService;

public class CreateDiagnosisPage extends MainBasePage {


	@SpringBean
	private DiagnosisService diagnosisService;
	
	@SpringBean
	private ImageAnalizer imageAnalizer;
	
	public CreateDiagnosisPage() {
		Diagnosis diagnosis = new Diagnosis();
		this.preparePage(diagnosis);
	}
	
	
	private void preparePage(Diagnosis diagnosis) {
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		Form<Diagnosis> form = new Form<Diagnosis>("form", new CompoundPropertyModel<Diagnosis>(Model.of(diagnosis)));
		add(form);
		
		final RequiredTextField<String> name = new RequiredTextField<String>("name");
		name.setLabel(Model.of("Name"));
		form.add(name);
		
		TextArea<String> description = new TextArea<String>("description");
		description.setLabel(Model.of("Description"));
		form.add(description);
		
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
		fileUploader.setOutputMarkupId(true);
		fileUploader.setRequired(true);
		fileUploader.setLabel(Model.of("Image"));
		form.add(fileUploader);
		
		
		AjaxButton submitButton = new AjaxButton("submitButton", Model.of("Create new Diagnosis")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Diagnosis diagnosis = (Diagnosis) form.getModelObject();
				FileUpload fileUpload = fileUploader.getFileUpload();
				
				String imagePath = fileUpload.getClientFileName();
				try {
					FileOutputStream fos = new FileOutputStream(imagePath);
					byte[] fileBytes = fileUpload.getBytes();
					fos.write(fileBytes);
					fos.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
				Double result = imageAnalizer.analize(imagePath);
				diagnosis.setResult(result);
				
				File tempImage = new File(imagePath);
				tempImage.delete();
				
				diagnosis.setOwner(loggedUser);
				diagnosisService.save(diagnosis);
				
				PageParameters params = new PageParameters();
				params.add("entityId", diagnosis.getId());
				setResponsePage(new ShowDiagnosisPage(params));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				List<String> messages = new ArrayList<String>();
				
				if (!name.getFeedbackMessages().isEmpty())
					messages.add(name.getFeedbackMessages().first().getMessage().toString());
				
				if (!techniques.getFeedbackMessages().isEmpty())
					messages.add(techniques.getFeedbackMessages().first().getMessage().toString());
				
				if (!fileUploader.getFeedbackMessages().isEmpty())
					messages.add(fileUploader.getFeedbackMessages().first().getMessage().toString());
				
				new Noty().show(messages, target);
			}
			
		};
		
		form.add(submitButton);
		
	}

	
}
