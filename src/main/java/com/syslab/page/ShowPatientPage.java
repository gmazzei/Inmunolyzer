package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.entity.Patient;
import com.syslab.service.PatientService;

public class ShowPatientPage extends MainBasePage {

	@SpringBean
	private PatientService patientService;
	
	public ShowPatientPage(PageParameters params) {
		
		Integer id = params.get("entityId").toInt();
		final Patient patient = patientService.getPatient(id);
		
		add(new Label("id", patient.getId()));
		add(new Label("name", patient.getName()));
		add(new Label("description", patient.getDescription()));
		
		AjaxLink<Void> editButton = new AjaxLink<Void>("editButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				PageParameters params = new PageParameters();
				params.add("entityId", patient.getId());
				setResponsePage(new UpdatePatientPage(params));
			}
		};
		
		AjaxLink<Void> deleteButton = new AjaxLink<Void>("deleteButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				patientService.delete(patient);
				setResponsePage(PatientListPage.class);
			}
		};
		
		add(editButton);
		add(deleteButton);
		add(new BookmarkablePageLink<PatientListPage>("returnButton", PatientListPage.class));
	}

}
