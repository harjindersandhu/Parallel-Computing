package progettoFinalTerm;


import java.util.Arrays;

public class SharpenerSerial {
	static float[][] sharpen(float[][] image) { //int[][]
		int[][] K ={
				{ 0, -1, 0 },
				{ -1, 5, -1 },
				{ 0, -1, 0}
				};
		int N = image.length;
		int M = image[0].length; 
		float somma;
		int[][] I = new int[N][M]; 
		
		//convert to (0,255)
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				
				I[i][j] = Math.max(0, Math.min(255, Math.round(image[i][j]*255))) ;
				//System.out.print("prima "+ image[i][j] + " dop "+ I[i][j]);
				
			}	
		}	
		float[][] O = new float[N][M];

		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				//O[i][j] = convolutePixel(i,j,I,K);
				somma=0;
				for(int l=0; l<K.length;l++){
					int x = i - (K.length/2) + l;
					if(x<0)
						x=0;
					if(x>(N-1))
						x=(N-1);
					for(int m=0; m< K.length;m++){
						int y = j - (K.length/2) + m;
						if(y<0)
							y=0;
						if(y>(M-1))
							y=(M-1);
						//System.out.print("valore originale i,j : "+i+","+j+":"+I[i][j]+"\n");
						somma += I[x][y]*K[l][m];
					}
					
				}
				
				//valore in 0-255
				if(somma>255)
					somma=255;
				if(somma<0)
					somma=0;
				//O[i][j] = somma;
				//valore in perc
				O[i][j]= somma/ 255;
				
			}	
		}	
		return O;
	}

}
