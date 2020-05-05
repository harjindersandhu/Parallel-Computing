package progettoFinalTerm;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SharpenerParallel {
	private static boolean launchThreads(int thread_number, Thread[] threads){
        for (int i = 0; i < thread_number; i++) {
            threads[i].start();
        }

        for (int i = 0; i < thread_number; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
	private static boolean launchThreadsPool(int thread_number, Thread[] threads){
        ExecutorService e = Executors.newFixedThreadPool(thread_number);
        for (int i = 0; i < thread_number; i++) {
            e.execute(threads[i]);
        }
        e.shutdown();
        while(!e.isTerminated()) {
        }
        return true;
    }

    static float[][] sharpen(float[][] v, int thread_num){
        int threads = 0;
        int N = v.length;
		int M = v[0].length; 
		int[][] I = new int[N][M]; 
        int[][] K ={
				{ 0, -1, 0 },
				{ -1, 5, -1 },
				{ 0, -1, 0}
				}; 
        //for(int i=0;i<N;i++){
		//	for(int j=0;j<M;j++){	
		//		I[i][j] = Math.max(0, Math.min(255, Math.round(v[i][j]*255))) ;
				//System.out.print(I[i][j]+"\n");
		//	}	
		//}
        //Number of threads the processor can handle at once
        //If the processor supports virtualization, this is 2*cores
        if (thread_num == 0) {
            threads = Runtime.getRuntime().availableProcessors();
        } else {
            threads = thread_num;
        }
        
        int size_per_thread = I.length/threads;
        float[][] newV = new float[N][M];
        PartialMatrixBuilder[] partialMatrixBuilders = new PartialMatrixBuilder[threads];
        
        
        for (int i = 0; i < threads-1; i++) {
            int start_t = i*size_per_thread;
            int end_t = i*size_per_thread + size_per_thread;
            partialMatrixBuilders[i] = new PartialMatrixBuilder(v, start_t, end_t, I);
        }
        partialMatrixBuilders[threads-1] = new PartialMatrixBuilder(v, (threads-1)*size_per_thread, v.length, I);
        launchThreads(threads, partialMatrixBuilders);
       
        PartialLookupThread[] partialLookupThreads = new PartialLookupThread[threads];
        for (int i = 0; i < threads-1; i++) {
            int start_t = i*size_per_thread;
            int end_t = i*size_per_thread + size_per_thread;
            partialLookupThreads[i] = new PartialLookupThread(I,K, start_t, end_t, newV);
        }
        partialLookupThreads[threads-1] = new PartialLookupThread(I,K, (threads-1)*size_per_thread, v.length, newV);
        launchThreads(threads, partialLookupThreads);

        
        
        return newV;
    }
}

