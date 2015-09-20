package com.syslab.page;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.gson.Gson;
import com.syslab.entity.Diagnosis;


public class ShowImagePage extends MainBasePage {
	
	public ShowImagePage(final PageParameters params, BufferedImage originalImage, BufferedImage transformedImage) {
		
		String json = params.get("entity").toString();
		Diagnosis diagnosis = new Gson().fromJson(json, Diagnosis.class);
		
		Image original = buildImageResource("original", originalImage);
		add(original);
			
		Image transformed = buildImageResource("transformed", transformedImage);
		add(transformed);			
		
		BigDecimal result = new BigDecimal(diagnosis.getResult());
		result = result.setScale(3, RoundingMode.HALF_UP);
		Label mitosisCellPercentage = new Label("mitosisCellPercentage", result + "%");
		add(mitosisCellPercentage);
		
		AjaxLink<ImageDetailsPage> continueButton = new AjaxLink<ImageDetailsPage>("continue") {	
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new ImageDetailsPage(params));
			}
		};
		
		add(continueButton);
		
		AjaxLink<ImageDetailsPage> returnButton = new AjaxLink<ImageDetailsPage>("return") {	
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(ImageAnalisisPage.class);
			}
		};
		
		add(returnButton);
	}
	
	
	private Image buildImageResource(String id, BufferedImage image) {
		BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
		resource.setImage(image);
		return new Image(id, resource);
	}
	
}
