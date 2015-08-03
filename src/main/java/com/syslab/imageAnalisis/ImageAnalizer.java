package com.syslab.imageAnalisis;

import org.springframework.stereotype.Service;

@Service
public class ImageAnalizer {
	
	public Double analize(byte[] image) {
		return Math.random() * 100.0; //Hardcodeado
	}
	
}
