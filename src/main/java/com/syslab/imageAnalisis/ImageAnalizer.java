package com.syslab.imageAnalisis;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageAnalizer {
	
	public Double analize(String imagePath) {
		
		try {
			Image img = ImageIO.read(new File(imagePath));
			BufferedImage bImage = (BufferedImage) img;			
			
			Map<Color, Integer> colors = this.getColorCount(bImage);
			
			System.out.println("Colors");
			System.out.println("-------");
			for (Map.Entry<Color, Integer> entry : colors.entrySet()) {
			    Color key = entry.getKey();
			    Integer value = entry.getValue();
			    System.out.println("RGB code: (" + key.getRed() + "," + key.getGreen() + "," + key.getBlue() + ") => Count: " + value);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		return Math.random() * 100.0; //Hardcodeado		
	}
	
	
	private Map<Color, Integer> getColorCount(BufferedImage bufferedImage) {
		final Map<Color, Integer> colorsMap = new HashMap<Color, Integer>();
		
		//Basic colors
		colorsMap.put(Color.RED, 0);
		colorsMap.put(Color.GREEN, 0);
		colorsMap.put(Color.BLUE, 0);
		colorsMap.put(Color.WHITE, 0);
		colorsMap.put(Color.BLACK, 0);
		
		//int total = bufferedImage.getWidth();
		
		for (int x = 0; x < bufferedImage.getWidth(); x++) {
			for (int y = 0; y < bufferedImage.getHeight(); y++) {
				//System.out.println(x * 100 / total);
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

	/*
	private Map<Color, Integer> getColorCount(BufferedImage bufferedImage) {
		final Map<Color, Integer> colors = new HashMap<Color, Integer>();
		
		int red = Color.RED.getRGB();
		int green = Color.GREEN.getRGB();
		int blue = Color.BLUE.getRGB();
		
		for (int x = 0; x < bufferedImage.getWidth(); x++) {
			for (int y = 0; y < bufferedImage.getHeight(); y++) {
				Color pixelColor = new Color(bufferedImage.getRGB(x, y));
				
				Integer count = colors.get(pixelColor);
				if (count == null) {
					colors.put(pixelColor, 1);
				} else {
					colors.put(pixelColor, count + 1);
				}
			}			
		}
		
		return colors;
	}
	*/
	
}
