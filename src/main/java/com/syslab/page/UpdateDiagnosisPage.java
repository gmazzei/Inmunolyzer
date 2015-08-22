package com.syslab.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.component.Noty;
import com.syslab.entity.Diagnosis;
import com.syslab.imageAnalisis.ImageAnalizer;
import com.syslab.service.DiagnosisService;

public class UpdateDiagnosisPage extends MainBasePage {


	@SpringBean
	private DiagnosisService diagnosisService;
	
	@SpringBean
	private ImageAnalizer imageAnalizer;
	
	public UpdateDiagnosisPage() {
		Diagnosis diagnosis = new Diagnosis();
		this.preparePage(diagnosis);
	}
	
	public UpdateDiagnosisPage(PageParameters params) {
		Integer id = params.get("entityId").toInteger();
		Diagnosis diagnosis = diagnosisService.getDiagnosis(id);
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
				
		
		AjaxButton submitButton = new AjaxButton("submitButton", Model.of("Save")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Diagnosis diagnosis = (Diagnosis) form.getModelObject();
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
				
				new Noty().show(messages, target);
			}
			
		};
		
		form.add(submitButton);
		
	}

	
}
