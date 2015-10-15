package com.syslab.imageAnalisis;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RestImageAnalyzer {
	
	public AnalisisResult analyze(BufferedImage image) {
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			byte[] bytes = baos.toByteArray();
			
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("image", ImageUtils.encode(bytes));
			String response = restTemplate.postForObject("http://127.0.0.1:8888/imageAnalyzer/index", map, String.class);
			JsonObject json = new Gson().fromJson(response, JsonObject.class);
			byte[] bytesReceived = ImageUtils.decode(json.get("image").getAsString());
			
			//TODO: Change null
			return null;

		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
