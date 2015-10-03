package com.syslab.page;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.StringUtils;

import com.syslab.entity.Patient;
import com.syslab.service.PatientService;

@Transactional
public class PatientListPage extends MainBasePage {

	@SpringBean
	private PatientService patientService;
	
	public PatientListPage() {
		
		//Create Button
		add(new BookmarkablePageLink<CreatePatientPage>("create", CreatePatientPage.class));
		
		final WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);
		
		
		//Search Field
		final TextField<String> searchField = new TextField<String>("search", Model.of(new String()));
		searchField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(container);
			}
		});
		add(searchField);
		
		
		
		//Table
		IModel<List<Patient>> model = new LoadableDetachableModel<List<Patient>>() {

			@Override
			protected List<Patient> load() {
				String name = searchField.getModelObject();
				if (StringUtils.isEmpty(name)) {
					return loggedUser.getPatients();
				} else {
					List<Patient> list = new ArrayList<Patient>();
					for (Patient patient : loggedUser.getPatients()) {
						if (patient.getName().contains(name)) list.add(patient);
					}
					return list;
				}
			}
		};
		
		ListView<Patient> listView = new ListView<Patient>("listView", model) {

			@Override
			protected void populateItem(ListItem<Patient> item) {
				final Patient patient = item.getModelObject();
				
				Label name = new Label("name", patient.getName());
				item.add(name);
				
				Label numberOfDiagnoses = new Label("numberOfDiagnoses", patient.getDiagnoses().size());
				item.add(numberOfDiagnoses);
				
				AjaxLink<Void> showButton = new AjaxLink<Void>("showButton") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						PageParameters params = new PageParameters();
						params.add("entityId", patient.getId());
						setResponsePage(new ShowPatientPage(params));
					}
				};
				
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
						loadLoggedUser();
						target.add(container);
					}
				};
				
				item.add(showButton);
				item.add(editButton);
				item.add(deleteButton);
			}
		};
		
		container.add(listView);
		
	}

}
