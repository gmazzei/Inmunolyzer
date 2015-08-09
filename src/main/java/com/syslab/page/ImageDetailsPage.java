package com.syslab.page;

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

import com.google.gson.Gson;
import com.syslab.entity.Diagnosis;
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
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		Form<Diagnosis> form = new Form<Diagnosis>("form", new CompoundPropertyModel<Diagnosis>(Model.of(diagnosis)));
		add(form);
		
		RequiredTextField<String> name = new RequiredTextField<String>("name");
		name.setLabel(Model.of("Name"));
		form.add(name);
		
		TextArea<String> description = new TextArea<String>("description");
		description.setLabel(Model.of("Description"));
		form.add(description);
		
		AjaxButton submitButton = new AjaxButton("submitButton", Model.of("Create new Diagnosis")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavaScript("noty({text: 'Saved!', layout: 'center', type: 'success', modal: 'true'});");
				Diagnosis diagnosis = (Diagnosis) form.getModelObject();
				diagnosis.setOwner(loggedUser);
				diagnosisService.save(diagnosis);
				PageParameters params = new PageParameters();
				params.add("entityId", diagnosis.getId());
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
