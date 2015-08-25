package com.syslab.page;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class ShowImagePage extends MainBasePage {
	
	public ShowImagePage(final PageParameters params) {
		String originalPath = params.get("originalPath").toString();
		Image original = buildImageResource("original", originalPath);
		add(original);

		String transformedPath = params.get("transformedPath").toString();
		Image transformed = buildImageResource("transformed", transformedPath);
		add(transformed);
		
		deleteTemporaryFile(originalPath);
		deleteTemporaryFile(transformedPath);
		
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
	
	
	private Image buildImageResource(String id, String path) {
		try {
			BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
			BufferedImage bImage = ImageIO.read(new File(path));
			resource.setImage(bImage);
			return new Image(id, resource);			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void deleteTemporaryFile(String path) {
		File image = new File(path);
		image.delete();
	}
	
}
