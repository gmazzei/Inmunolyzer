package com.syslab.imageAnalisis;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.springframework.security.crypto.codec.Base64;

public class ImageUtils {
	
	public static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
    public static BufferedImage toBufferedImage(Mat m){
    	int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);  
        return image;
    }
    
    public static Mat toMat(BufferedImage image) {
    	Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
    	byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    	mat.put(0, 0, pixels);
    	return mat;
    }
    
    public static byte[] decode(String string) {
		return Base64.decode(string.getBytes()); 
    }
    
    public static String encode(byte[] bytes) {
    	return new String(Base64.encode(bytes));
    }
	
}
