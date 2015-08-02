package com.syslab.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.syslab.entity.Diagnosis;
import com.syslab.service.DiagnosisService;

public class ShowDiagnosisPage extends BasePage {

	@SpringBean
	private DiagnosisService diagnosisService;
	
	public ShowDiagnosisPage(PageParameters params) {
		
		Integer id = params.get("entityId").toInt();
		final Diagnosis diagnosis = diagnosisService.getDiagnosis(id);
		
		add(new Label("id", diagnosis.getId()));
		add(new Label("name", diagnosis.getName()));
		add(new Label("technique", diagnosis.getTechnique()));
		add(new Label("result", diagnosis.getResult()));
		add(new Label("description", diagnosis.getDescription()));
		
		AjaxLink<Void> editButton = new AjaxLink<Void>("editButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				PageParameters params = new PageParameters();
				params.add("entityId", diagnosis.getId());
				setResponsePage(CreateDiagnosisPage.class, params);
			}
		};
		
		AjaxLink<Void> deleteButton = new AjaxLink<Void>("deleteButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				diagnosisService.delete(diagnosis);
				setResponsePage(DiagnosisListPage.class);
			}
		};
		
		add(editButton);
		add(deleteButton);
		add(new BookmarkablePageLink<DiagnosisListPage>("returnButton", DiagnosisListPage.class));
	}

}
