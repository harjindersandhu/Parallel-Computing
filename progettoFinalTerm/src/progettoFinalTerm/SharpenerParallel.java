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
       
        if (thread_num == 0) {
            threads = Runtime.getRuntime().availableProcessors();
        } else {
            threads = thread_num;
        }
        
        int length_per_thread = I.length/threads;
        float[][] newV = new float[N][M];
        PartialMatrixBuilder[] partialMatrixBuilders = new PartialMatrixBuilder[threads];
        
        
        for (int i = 0; i < threads-1; i++) {
            int start_t = i*size_per_thread;
            int end_t = i*size_per_thread + size_per_thread;
            partialMatrixBuilders[i] = new PartialMatrixBuilder(v, start_t, end_t, I);
        }
        partialMatrixBuilders[threads-1] = new PartialMatrixBuilder(v, (threads-1)*length_per_thread, v.length, I);
        launchThreads(threads, partialMatrixBuilders);
       
        ConvoluteThread[] sonvoluteThreads = new ConvoluteThread[threads];
        for (int i = 0; i < threads-1; i++) {
            int start_t = i*length_per_thread;
            int end_t = i*length_per_thread + length_per_thread;
            convoluteThreads[i] = new ConvoluteThread(I,K, start_t, end_t, newV);
        }
        convoluteThreads[threads-1] = new ConvoluteThread(I,K, (threads-1)*length_per_thread, v.length, newV);
        launchThreads(threads, convoluteThreads);

        
        
        return newV;
    }
}

