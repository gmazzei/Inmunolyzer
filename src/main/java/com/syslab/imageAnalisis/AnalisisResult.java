package com.syslab.imageAnalisis;

import java.awt.Image;

public class AnalisisResult {
	
	private Integer goodCellCount;
	private Integer badCellCount;
	private Double goodCellPercentage;
	private Double badCellPercentage;
	private Image originalImage;
	private Image transformedImage;
	
	
	
	public Integer getGoodCellCount() {
		return goodCellCount;
	}
	public void setGoodCellCount(Integer goodCellCount) {
		this.goodCellCount = goodCellCount;
	}
	public Integer getBadCellCount() {
		return badCellCount;
	}
	public void setBadCellCount(Integer badCellCount) {
		this.badCellCount = badCellCount;
	}
	public Double getGoodCellPercentage() {
		return goodCellPercentage;
	}
	public void setGoodCellPercentage(Double goodCellPercentage) {
		this.goodCellPercentage = goodCellPercentage;
	}
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
