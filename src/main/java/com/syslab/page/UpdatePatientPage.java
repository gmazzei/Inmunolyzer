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
import com.syslab.entity.Patient;
import com.syslab.service.PatientService;

public class UpdatePatientPage extends MainBasePage {


	@SpringBean
	private PatientService patientService;
	
	
	public UpdatePatientPage() {
		Patient patient = new Patient();
		this.preparePage(patient);
	}
	
	public UpdatePatientPage(PageParameters params) {
		Integer id = params.get("entityId").toInteger();
		Patient patient = patientService.getPatient(id);
		this.preparePage(patient);
	}
	
	private void preparePage(Patient patient) {
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		Form<Patient> form = new Form<Patient>("form", new CompoundPropertyModel<Patient>(Model.of(patient)));
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
				Patient patient = (Patient) form.getModelObject();
				patientService.save(patient);
				
				PageParameters params = new PageParameters();
				params.add("entityId", patient.getId());
				setResponsePage(new ShowPatientPage(params));
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
