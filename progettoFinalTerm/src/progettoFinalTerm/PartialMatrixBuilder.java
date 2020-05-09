package progettoFinalTerm;

public class PartialMatrixBuilder extends Thread {
    private final float[][] v;
    private final int[][] newV;
    private final int start;
    private final int end;


    PartialMatrixBuilder(float[][] v, int start, int end,int[][] newV){
        this.v = v;
        this.newV = newV;
        this.start = start;
        this.end = end;
       
    }
    
    @Override
    public void run() {
    		for(int i=start;i<end;i++){
			for(int j=0;j<v[0].length;j++){	
				newV[i][j] = Math.max(0, Math.min(255, Math.round(v[i][j]*255))) ;
			}	
		}
    	
    	
    	
        
    }
}
