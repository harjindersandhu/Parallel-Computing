package progettoFinalTerm;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		String path = "images/Vd-Orig.png";
		File f1 = new File(path);
		BufferedImage img;
	    try {
	        img = ImageIO.read(f1);
	    } catch (IOException e) {
	        e.printStackTrace();
	        img = new BufferedImage(10,10, BufferedImage.TYPE_INT_RGB );//);
	    }
	    //System.out.print(img.getData().getSample(0, 0, 0));
	    HSVImage hsv_img = new HSVImage(img);
	    
	    float[][] newV_serial = new float[hsv_img.getWidth()][hsv_img.getHeight()];
	    
	    
        long start = System.nanoTime();
        newV_serial = SharpenerSerial.sharpen(hsv_img.getV());
        //SharpenerSerial.sharpen(hsv_img.getV());
        long duration_serial = System.nanoTime() - start;
        double seconds_serial = (double)duration_serial / 1000000000.0;
        System.out.print(seconds_serial);
	    //newV_serial = SharpenerSerial.sharpen(hsv_img.getV());
	    
        hsv_img.setV(newV_serial);

        BufferedImage equalized = hsv_img.getRGBImage();

        File outputfile = new File("imagesSharpened/equalized_serial.png");
        try {
            ImageIO.write(equalized, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
	}
