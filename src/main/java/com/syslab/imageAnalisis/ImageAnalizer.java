package com.syslab.imageAnalisis;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

@Service
public class ImageAnalizer {
	
	private static final Scalar LOW_BAD_CELL = new Scalar(7,0,0);
	private static final Scalar HIGH_BAD_CELL = new Scalar(30,255,255);
	private static final Scalar LOW_GOOD_CELL = new Scalar(90,30,0);
	private static final Scalar HIGH_GOOD_CELL = new Scalar(127,255,255);
	
	public ImageAnalizer() {
		setupOpenCV();
	}
	
	private void setupOpenCV() {
		try {
			addFiles("openCV/lib");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void addFiles(String path) {
		File folder = new File(path);
		for (File file : folder.listFiles()) {
			String filePath = file.getAbsolutePath();
			if (filePath.endsWith("so")) {
				System.load(filePath);
			}
		}
	}
	
	public AnalisisResult analize(BufferedImage image) {
		
		Map<String, Object> resultMap = this.getAnalisisResults(image);
		
		BufferedImage transformedImage = (BufferedImage) resultMap.get("transformedImage");
		Integer goodCells = (Integer) resultMap.get("goodCellCount");
		Integer badCells = (Integer) resultMap.get("badCellCount");
		Integer totalCells = goodCells + badCells;
		
		AnalisisResult result = new AnalisisResult();
		result.setGoodCellCount(goodCells);
		result.setBadCellCount(badCells);
		result.setGoodCellPercentage(goodCells * 100.0 / totalCells);
		result.setBadCellPercentage(badCells * 100.0 / totalCells);
		result.setOriginalImage(image);
		result.setTransformedImage(transformedImage);
		
		return result;
	}

	
	private Map<String, Object> getAnalisisResults(BufferedImage image) {
	    Mat src = ImageUtils.toMat(image);
	    
	    Mat hsvsrc = new Mat();
	    Mat badCellFilter = new Mat();
	    Mat goodCellFilter = new Mat();
	    
	    Imgproc.cvtColor(src, hsvsrc, Imgproc.COLOR_BGR2HSV);
	    	
	    Core.inRange(hsvsrc, LOW_BAD_CELL, HIGH_BAD_CELL, badCellFilter);
	    Core.inRange(hsvsrc, LOW_GOOD_CELL, HIGH_GOOD_CELL, goodCellFilter);
	    
	    BufferedImage transformedImage = ImageUtils.toBufferedImage(badCellFilter);
	    
	    
	    Integer nonZeroBadCell = Core.countNonZero(badCellFilter);
	    Integer nonZeroGoodCell = Core.countNonZero(goodCellFilter);
	    
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("goodCellCount", nonZeroGoodCell);
		map.put("badCellCount", nonZeroBadCell);
		map.put("transformedImage", transformedImage);
		return map;
	}	
	
}
