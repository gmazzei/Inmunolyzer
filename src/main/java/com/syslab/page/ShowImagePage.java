package com.syslab.page;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.gson.Gson;
import com.syslab.entity.Diagnosis;
import com.syslab.imageAnalysis.ImageUtils;


public class ShowImagePage extends MainBasePage {
	
	public ShowImagePage(final PageParameters params) {
		
		String json = params.get("entity").toString();
		Diagnosis diagnosis = new Gson().fromJson(json, Diagnosis.class);
		
		
		byte[] originalBytes = ImageUtils.decode(params.get("original").toString());
		BufferedImage originalImage = ImageUtils.toImage(originalBytes);
		Image originalImageResource = buildImageResource("original", originalImage);
		add(originalImageResource);
		
		byte[] transformedBytes = ImageUtils.decode(params.get("transformed").toString());
		BufferedImage transformedImage = ImageUtils.toImage(transformedBytes);
		Image transformedImageResource = buildImageResource("transformed", transformedImage);
		add(transformedImageResource);			
		
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
				setResponsePage(ImageAnalysisPage.class);
			}
		};
		
		add(returnButton);
	}
	
	
	private Image buildImageResource(String id, BufferedImage image) {
		BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
		resource.setImage(image);
		return new NonCachingImage(id, resource);
	}
	
}
