package progettoFinalTerm;

public class ConvoluteThread extends Thread {
    private final int[][] v;
    private final float[][] newV;
    private final int[][] K;
    private final int start;
    private final int end;


    ConvoluteThread(int[][] v,int[][] K, int start, int end,float[][] newV){
        this.v = v;
        this.newV = newV;
        this.K = K;
        this.start = start;
        this.end = end;
       
    }
    

    @Override
    public void run() {
    	float somma;
    	int M = v[0].length;
    	int N = v.length;
    //convulute pixel in submatrix (start-end)xM 
    	for(int i=start;i<end;i++){
			for(int j=0;j<M;j++){
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
						somma += v[x][y]*K[l][m];
					}
				}
				if(somma>255)
					somma=255;
				if(somma<0)
					somma=0;
				newV[i][j]= somma/ 255;
			}	
		}  
    }
}