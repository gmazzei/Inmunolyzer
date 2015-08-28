package com.syslab.imageAnalisis;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ImageAnalizer {
	
	private static final Color BACKGROUND = new Color(253, 232, 170);
	private static final Color NORMAL_CELL = new Color(199, 195, 141);
	private static final Color BAD_CELL = new Color(134, 91, 57);
	private static final Color FRAME = Color.BLACK;
	
	public AnalisisResult analize(Image image) {
		
		BufferedImage original = (BufferedImage) image;
		BufferedImage transformed = ImageUtils.deepCopy(original);
		
		Map<Color, Integer> colorCount = this.getColorCount(transformed);
		Double badCellPercentage = colorCount.get(BAD_CELL).doubleValue() * 100 / (colorCount.get(NORMAL_CELL).doubleValue() + colorCount.get(BAD_CELL).doubleValue());
		
		AnalisisResult result = new AnalisisResult();
		result.setBadCellPercentage(badCellPercentage.doubleValue());
		result.setOriginalImage(image);
		result.setTransformedImage(transformed);
		return result;
	}

	
	private Map<Color, Integer> getColorCount(BufferedImage bufferedImage) {
		Map<Color, Integer> colorsMap = new HashMap<Color, Integer>();
		Map<Color, Color> complementMap = new HashMap<Color, Color>();
		
		//Color count
		colorsMap.put(BACKGROUND, 0);
		colorsMap.put(NORMAL_CELL, 0);
		colorsMap.put(BAD_CELL, 0);
		colorsMap.put(FRAME, 0);
		
		complementMap.put(BACKGROUND, Color.GRAY);
		complementMap.put(NORMAL_CELL, Color.BLUE);
		complementMap.put(BAD_CELL, Color.RED);
		complementMap.put(FRAME, Color.DARK_GRAY);
		
		
		for (int x = 0; x < bufferedImage.getWidth(); x++) {
			for (int y = 0; y < bufferedImage.getHeight(); y++) {
				Color color = new Color(bufferedImage.getRGB(x, y));
				Double minorDistance = Double.MAX_VALUE;
				
				Color closestColor = null;
				Double distance;
				Color basicColor;
				
				for (Map.Entry<Color, Integer> entry : colorsMap.entrySet()) {
				    basicColor = entry.getKey();
				    distance = calculateDistance(color, basicColor);
				    
				    if (distance <= minorDistance) {
				    	closestColor = basicColor;
				    	minorDistance = distance;
				    }
				}
				
				int count = colorsMap.get(closestColor);
				colorsMap.put(closestColor, count + 1);
				
				Color complement = complementMap.get(closestColor);
				bufferedImage.setRGB(x, y, complement.getRGB());
			}
		}
		
		return colorsMap;
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
	
}
