package progettoFinalTerm;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		String path = "images/firenze.png";
		File f1 = new File(path);
		BufferedImage img;
	    try {
	        img = ImageIO.read(f1);
	    } catch (IOException e) {
	        e.printStackTrace();
	        img = new BufferedImage(10,10, BufferedImage.TYPE_INT_RGB );//);
	    }
	    HSVImage hsv_img = new HSVImage(img);
	    
	    //Serial Version 
	    
	    /*
	    float[][] newV_serial = new float[hsv_img.getWidth()][hsv_img.getHeight()];
	    long start = System.nanoTime();
	    newV_serial = SharpenerSerial.sharpen(hsv_img.getV());
	    long duration_serial = System.nanoTime() - start;
        double seconds = (double)duration_serial / 1000000000.0;
        File outputfile = new File("imagesSharpened/equalized_serial.png");
	    */
	    
	    //parallel version
        
	    float[][] newV_parallel = new float[hsv_img.getWidth()][hsv_img.getHeight()];
        long start = System.nanoTime();
        newV_parallel = SharpenerParallel.sharpen(hsv_img.getV(), 2);
        long duration_serial = System.nanoTime() - start;
        double seconds = (double)duration_serial / 1000000000.0;
        hsv_img.setV(newV_parallel);
        File outputfile = new File("imagesSharpened/equalized_parallel.png");
        
        
        
        
        System.out.print(seconds);
        BufferedImage equalized = hsv_img.getRGBImage();
      
        try {
            ImageIO.write(equalized, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
	}
