package com.syslab.imageAnalisis;

import java.awt.Image;

public class AnalisisResult {
	
	private Double badCellPercentage;
	private Image originalImage;
	private Image transformedImage;
	
	
	public Double getBadCellPercentage() {
		return badCellPercentage;
	}
	public void setBadCellPercentage(Double badCellPercentage) {
		this.badCellPercentage = badCellPercentage;
	}
	public Image getOriginalImage() {
		return originalImage;
	}
	public void setOriginalImage(Image originalImage) {
		this.originalImage = originalImage;
	}
	public Image getTransformedImage() {
		return transformedImage;
	}
	public void setTransformedImage(Image transformedImage) {
		this.transformedImage = transformedImage;
	}
	
}
