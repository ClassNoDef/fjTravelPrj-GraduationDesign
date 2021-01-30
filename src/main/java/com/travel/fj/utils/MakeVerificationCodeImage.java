package com.travel.fj.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class MakeVerificationCodeImage {

	private static BufferedImage createImage(String securityCode) {
		 
	       
	       int codeLength = securityCode.length();
	       
	       int fSize = 18;
	       int fWidth = fSize + 1;
	       
	       int width = codeLength * fWidth + 20;
	     
	       int height = fSize * 2 + 10;
	       
	       Random rand = new Random();

	       
	       BufferedImage image = new BufferedImage(width, height,
	              BufferedImage.TYPE_INT_RGB);
	       Graphics g = image.createGraphics();
	 
	      
	       g.setColor(Color.WHITE);
	      
	       g.fillRect(0, 0, width, height);
	 
	       
	       g.setColor(Color.DARK_GRAY);
	       
	       g.setFont(new Font("Arial", Font.BOLD, height - 2));
	      
	       g.drawRect(0, 0, width - 1, height - 1);
	 
	      
	       
	       
	       g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	       for (int i = 0; i < codeLength * 6; i++) {
	           int x = rand.nextInt(width);
	           int y = rand.nextInt(height);
	           
	           g.drawRect(x, y, 1, 1);
	       }
	       for(int i=0;i<4;i++){
		         g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
		         
		         g.drawLine(rand.nextInt(100), rand.nextInt(100), rand.nextInt(100), rand.nextInt(100));
		     }
	 
	       
	       int codeY = 2* height / 3;
	       
	       g.setFont(new Font("Georgia", Font.BOLD, fSize));
	       for (int i = 0; i < codeLength; i++) {
	           g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	           g.drawString(String.valueOf(securityCode.charAt(i)), i*20+10,
	        		   codeY);
	       }
	       
	       g.dispose();
	 
	       return image;
	    }
	 
	   	
	    public static ByteArrayInputStream getImageAsInputStream(String securityCode) {
	 
	       BufferedImage image = createImage(securityCode);
	       return convertImageToStream(image);
	    }
	 
	   
	    private static ByteArrayInputStream convertImageToStream(BufferedImage image) {
	 
	       ByteArrayInputStream inputStream = null;
	       ByteArrayOutputStream bos = new ByteArrayOutputStream();
	       try {
	           ImageIO.write(image, "jpeg", bos);
	           byte[] bts = bos.toByteArray();
	           inputStream = new ByteArrayInputStream(bts);
	       }  catch (IOException e) {
	           e.printStackTrace();
	       }
	       return inputStream;
	    }


}
