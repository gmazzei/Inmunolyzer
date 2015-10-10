package com.syslab.component.validator;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class ImageValidator implements IValidator<List<FileUpload>> {
	
	private static final List<String> VALID_FORMATS = Arrays.asList(".jpg", ".png");
	
	
	@Override
	public void validate(IValidatable<List<FileUpload>> validatable) {
		final List<FileUpload> fileUploadList = validatable.getValue();
		FileUpload upload = fileUploadList.get(0);
		String imageName = upload.getClientFileName();
		
		if (!hasValidFormat(imageName)) {
			error(validatable, "Image must have JPG or PNG format.");
		}
		
	}

	private boolean hasValidFormat(String imageName) {
		String name = imageName.toLowerCase();
		for (String formatSuffix : VALID_FORMATS) {
			if (name.endsWith(formatSuffix)) {
				return true;
			}
		}
		
		return false;
	}
	
	private void error(IValidatable<List<FileUpload>> validatable, String message) {
		ValidationError error = new ValidationError(message);
		validatable.error(error);
	}
	
	
	
}
