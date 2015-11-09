package com.syslab.imageAnalysis;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;

@Service
public class ImageAnalyzer {
	
	private static final Scalar LOW_BAD_CELL = new Scalar(0,0,255); // RED
	private static final Scalar HIGH_BAD_CELL = new Scalar(0,0,255); // RED
	private static final Scalar LOW_GOOD_CELL = new Scalar(255,0,0); // BLUE
	private static final Scalar HIGH_GOOD_CELL = new Scalar(255,0,0); // BLUE
	
	private static final Color BACKGROUND = new Color(252, 245, 254);
	private static final Color NORMAL_CELL = new Color(145, 154, 197);
	private static final Color NORMAL_CELL_2 = new Color(20, 29, 150);
	private static final Color BAD_CELL = new Color(87, 53, 47);
	private static final Color BAD_CELL_2 = new Color(162, 142, 148);
	
	public ImageAnalyzer() {
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
	
	public AnalysisResult analyze(BufferedImage image) {
		
		Map<String, Object> resultMap = this.getAnalysisResults(image);
		
		BufferedImage transformedImage = (BufferedImage) resultMap.get("transformedImage");
		Integer goodCells = (Integer) resultMap.get("goodCellCount");
		Integer badCells = (Integer) resultMap.get("badCellCount");
		Integer totalCells = goodCells + badCells;
		
		if (totalCells == 0) totalCells = 1;
		
		AnalysisResult result = new AnalysisResult();
		result.setGoodCellCount(goodCells);
		result.setBadCellCount(badCells);
		result.setGoodCellPercentage(goodCells * 100.0 / totalCells);
		result.setBadCellPercentage(badCells * 100.0 / totalCells);
		result.setOriginalImage(image);
		result.setTransformedImage(transformedImage);
		
		return result;
	}

	
	private Map<String, Object> getAnalysisResults(BufferedImage image) {
    	
	    Mat src = ImageUtils.toMat(image);
	    
	    //Mat hsvsrc = new Mat();
	    Mat filteredMat = new Mat();
	    Mat badCellFilter = new Mat();
	    Mat goodCellFilter = new Mat();
	    
	    
	    //Imgproc.cvtColor(src, hsvsrc, Imgproc.COLOR_BGR2HSV);
	    //filteredMat = colorFilter(hsvsrc);
	    filteredMat = colorFilter(src);
	    
	    
	    Core.inRange(filteredMat, LOW_BAD_CELL, HIGH_BAD_CELL, badCellFilter);
	    Core.inRange(filteredMat, LOW_GOOD_CELL, HIGH_GOOD_CELL, goodCellFilter);
	    
	    Integer nonZeroBadCell = Core.countNonZero(badCellFilter);
	    Integer nonZeroGoodCell = Core.countNonZero(goodCellFilter);
	    
	    
	    //Para pruebas
	    
	    /*
	    Imgcodecs.imwrite("openCV/src.jpg", src);
	    //Imgcodecs.imwrite("openCV/hsvsrc.jpg", hsvsrc);
	    Imgcodecs.imwrite("openCV/filteredMat.jpg", filteredMat);
	    Imgcodecs.imwrite("openCV/goodCellFilter.jpg", goodCellFilter);
	    Imgcodecs.imwrite("openCV/badCellFilter.jpg", badCellFilter);
	    */
	    
	    BufferedImage transformedImage = ImageUtils.toBufferedImage(filteredMat);
	    
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("goodCellCount", nonZeroGoodCell);
		map.put("badCellCount", nonZeroBadCell);
		map.put("transformedImage", transformedImage);
		return map;
	}
	
	
	
	
	
	private Mat colorFilter(Mat mat) {
		
		Map<Color, Color> complementMap = new HashMap<Color, Color>();
		
		complementMap.put(BACKGROUND, Color.WHITE);
		complementMap.put(NORMAL_CELL, Color.BLUE);
		complementMap.put(NORMAL_CELL_2, Color.BLUE);
		complementMap.put(BAD_CELL, Color.RED);
		complementMap.put(BAD_CELL_2, Color.RED);
		
		BufferedImage bufferedImage = ImageUtils.toBufferedImage(mat);
		
		for (int x = 0; x < bufferedImage.getWidth(); x++) {
			for (int y = 0; y < bufferedImage.getHeight(); y++) {
				Color color = new Color(bufferedImage.getRGB(x, y));
				Double minorDistance = Double.MAX_VALUE;
				
				Color closestColor = null;
				Double distance;
				Color basicColor;
				
				for (Map.Entry<Color, Color> entry : complementMap.entrySet()) {
				    basicColor = entry.getKey();
				    distance = calculateDistance(color, basicColor);
				    
				    if (distance <= minorDistance) {
				    	closestColor = basicColor;
				    	minorDistance = distance;
				    }
				}
				
				Color complement = complementMap.get(closestColor);
				bufferedImage.setRGB(x, y, complement.getRGB());
			}
		}
		
		return ImageUtils.toMat(bufferedImage);
	}
	
	private Double calculateDistance(Color firstColor, Color secondColor) {
		int r1 = firstColor.getRed();
		int r2 = secondColor.getRed();
		int g1 = firstColor.getGreen();
		int g2 = secondColor.getGreen();
		int b1 = firstColor.getBlue();
		int b2 = secondColor.getBlue();
		
		return Math.sqrt(Math.pow(r2 - r1, 2) + Math.pow(g2 - g1, 2) + Math.pow(b2 - b1, 2));
	}
	
	
	//Pinta de verde el borde de las celulas malas
	private Mat highlightCells(Mat mat) {
		
		Color background = Color.WHITE;
		Color goodCell = Color.BLUE;
		Color badCell = Color.RED;
		Color frame = Color.GREEN;
		
		BufferedImage image = ImageUtils.toBufferedImage(mat);
		
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight()-1; y++) {
				Color color = new Color(image.getRGB(x, y));
				Color nextColor = new Color(image.getRGB(x, y + 1));
				
				if ((color.equals(background) || color.equals(goodCell)) && nextColor.equals(badCell)) {
					image.setRGB(x, y, frame.getRGB());
				}
			}
		}
		
		return ImageUtils.toMat(image);
	}
	
}
