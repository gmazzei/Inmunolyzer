package com.syslab.page;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.gson.Gson;
import com.syslab.component.Noty;
import com.syslab.component.validator.ImageValidator;
import com.syslab.entity.Diagnosis;
import com.syslab.entity.Technique;
import com.syslab.imageAnalysis.AnalysisResult;
import com.syslab.imageAnalysis.ImageAnalyzer;
import com.syslab.imageAnalysis.ImageUtils;

public class ImageAnalisysPage extends MainBasePage {
		
	@SpringBean
	private ImageAnalyzer imageAnalyzer;
	
	public ImageAnalisysPage() {
		
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
		fileUploader.add(new ImageValidator());
		form.add(fileUploader);
		
		
		AjaxButton ajaxButton = new AjaxButton("submit") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				try {					
					Diagnosis diagnosis = (Diagnosis) form.getModelObject();
					byte[] bytes = fileUploader.getFileUpload().getBytes();
					BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
					
					AnalysisResult analisisResult = imageAnalyzer.analyze(image);
					diagnosis.setResult(analisisResult.getBadCellPercentage().doubleValue());
					diagnosis.setGoodCellCount(analisisResult.getGoodCellCount());
					diagnosis.setBadCellCount(analisisResult.getBadCellCount());
					diagnosis.setGoodCellPercentage(analisisResult.getGoodCellPercentage());
					diagnosis.setBadCellPercentage(analisisResult.getBadCellPercentage());
					
					PageParameters params = new PageParameters();
					Gson gson = new Gson();
					String entity = gson.toJson(diagnosis);
					params.add("entity", entity);
					
					byte[] original = ImageUtils.toByteArray(image, "jpg");
					byte[] transformed = ImageUtils.toByteArray((BufferedImage)analisisResult.getTransformedImage(), "jpg");
					params.add("original", ImageUtils.encode(original));
					params.add("transformed", ImageUtils.encode(transformed));
					
					setResponsePage(new ShowImagePage(params));
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
							
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