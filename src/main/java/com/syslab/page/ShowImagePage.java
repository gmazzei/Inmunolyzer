package com.syslab.page;

import java.awt.image.BufferedImage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class ShowImagePage extends MainBasePage {
	
	public ShowImagePage(final PageParameters params, BufferedImage originalImage, BufferedImage transformedImage) {
		
		Image original = buildImageResource("original", originalImage);
		add(original);
			
		Image transformed = buildImageResource("transformed", transformedImage);
		add(transformed);			
		
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
