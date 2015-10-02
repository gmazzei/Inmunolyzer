package com.syslab.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.gson.Gson;
import com.syslab.entity.Diagnosis;
import com.syslab.entity.Patient;
import com.syslab.service.DiagnosisService;

public class ImageDetailsPage extends MainBasePage {


	@SpringBean
	private DiagnosisService diagnosisService;
	
	public ImageDetailsPage(PageParameters params) {
		String json = params.get("entity").toString();
		Diagnosis diagnosis = new Gson().fromJson(json, Diagnosis.class);
		this.preparePage(diagnosis);
	}
	
	private void preparePage(Diagnosis diagnosis) {
		
		Form<Diagnosis> form = new Form<Diagnosis>("form", new CompoundPropertyModel<Diagnosis>(Model.of(diagnosis)));
		add(form);
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		form.add(feedbackPanel);
		
		RequiredTextField<String> name = new RequiredTextField<String>("name");
		name.setLabel(Model.of("Name"));
		form.add(name);
		
		IModel<List<Patient>> patientModel = new LoadableDetachableModel<List<Patient>>() {

			@Override
			protected List<Patient> load() {
				return loggedUser.getPatients();
			}
			
		};
		
		final DropDownChoice<Patient> patient = new DropDownChoice<Patient>("patient", patientModel);
		patient.setRequired(true);
		patient.setLabel(Model.of("Patient"));
		form.add(patient);
		
		TextArea<String> description = new TextArea<String>("description");
		description.setLabel(Model.of("Description"));
		form.add(description);
		
		
		
		AjaxButton submitButton = new AjaxButton("submitButton", Model.of("Create new Diagnosis")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Diagnosis diagnosis = (Diagnosis) form.getModelObject();
				diagnosis.setPatient(patient.getModelObject());
				diagnosisService.save(diagnosis);
				PageParameters params = new PageParameters();
				params.add("entityId", diagnosis.getId());
				setResponsePage(new ShowDiagnosisPage(params));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
			
		};
		
		form.add(submitButton);
		
	}

	
}
