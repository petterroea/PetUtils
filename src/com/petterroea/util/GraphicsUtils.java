package com.petterroea.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GraphicsUtils {
	public static int getCenter(int w, String s, Graphics g)
	{
		return (w/2)-(g.getFontMetrics().stringWidth(s)/2);
	}
	public static void setAlpha(byte alpha, BufferedImage img, int x, int y) {       
	    alpha %= 0xff; 
	            int color = img.getRGB(x, y);

	            int mc = (alpha << 24) | 0x00ffffff;
	            int newcolor = color & mc;
	            img.setRGB(x, y, newcolor);            

	}
	public static BufferedImage scale(BufferedImage in, float scale)
	{
		BufferedImage out = new BufferedImage((int)((float)in.getWidth()*scale), (int)((float)in.getHeight()*scale), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < out.getWidth(); x++)
		{
			for(int y = 0; y < out.getHeight(); y++)
			{
				out.setRGB(x, y, in.getRGB((int)((float)x/scale), (int)((float)y/scale)));
			}
		}
		return out;
	}
}
