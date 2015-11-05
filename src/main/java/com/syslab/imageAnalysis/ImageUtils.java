package com.syslab.imageAnalysis;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

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
    	byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    	Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }
    
    public static byte[] decode(String string) {
		return Base64.decode(string.getBytes()); 
    }
    
    public static String encode(byte[] bytes) {
    	return new String(Base64.encode(bytes));
    }
    
    public static byte[] toByteArray(BufferedImage image, String format) {
    	try {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		ImageIO.write(image, format, baos);
    		baos.flush();
    		byte[] bytes = baos.toByteArray();
    		baos.close();
    		return bytes;	
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
    }
    
    public static BufferedImage toImage(byte[] bytes) {
    	try {
    		InputStream stream = new ByteArrayInputStream(bytes);
    		BufferedImage image = ImageIO.read(stream);
    		return image;
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
    	
    }
	
}
