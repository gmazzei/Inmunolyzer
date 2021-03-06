package com.syslab.page;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.syslab.entity.Diagnosis;
import com.syslab.service.DiagnosisService;

@Transactional
public class DiagnosisListPage extends MainBasePage {

	@SpringBean
	private DiagnosisService diagnosisService;
	
	public DiagnosisListPage() {
		
		//Create Button
		add(new BookmarkablePageLink<CreateDiagnosisPage>("create", CreateDiagnosisPage.class));
		
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
		IModel<List<Diagnosis>> model = new LoadableDetachableModel<List<Diagnosis>>() {

			@Override
			protected List<Diagnosis> load() {
				String name = searchField.getModelObject();
				if (StringUtils.isEmpty(name)) {
					return loggedUser.getDiagnoses();
				} else {
					name = name.toLowerCase();
					List<Diagnosis> list = new ArrayList<Diagnosis>();
					for (Diagnosis diagnosis : loggedUser.getDiagnoses()) {
						if (diagnosis.getName().toLowerCase().contains(name) || diagnosis.getPatient().getName().toLowerCase().contains(name)){
							list.add(diagnosis);
						} 
					}
					return list;
				}
			}
		};
		
		ListView<Diagnosis> listView = new ListView<Diagnosis>("listView", model) {

			@Override
			protected void populateItem(ListItem<Diagnosis> item) {
				final Diagnosis diagnosis = item.getModelObject();
				
				Label name = new Label("name", diagnosis.getName());
				item.add(name);
				
				Label patient = new Label("patient", diagnosis.getPatient().getName());
				item.add(patient);
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
				Label date = new Label("date", df.format(diagnosis.getCreationDate()));
				item.add(date);
				
				
				AjaxLink<Void> showButton = new AjaxLink<Void>("showButton") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						PageParameters params = new PageParameters();
						params.add("entityId", diagnosis.getId());
						setResponsePage(new ShowDiagnosisPage(params));
					}
				};
				
				AjaxLink<Void> editButton = new AjaxLink<Void>("editButton") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						PageParameters params = new PageParameters();
						params.add("entityId", diagnosis.getId());
						setResponsePage(new UpdateDiagnosisPage(params));
					}
				};
				
				AjaxLink<Void> deleteButton = new AjaxLink<Void>("deleteButton") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						diagnosisService.delete(diagnosis);
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
