#include <omp.h>
#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/imgcodecs/imgcodecs.hpp>



void cumulative_histogram(int hist[], int equalized[], int cols, int rows)

{

    int cumulative_histogram[256];

    cumulative_histogram[0] = hist[0];

    // Cumulative_histogram needs to be compute in sequential mode
    for(int i = 1; i < 256; i++)
    {
        // compute the cdf
        cumulative_histogram[i] = hist[i] + cumulative_histogram[i-1];
    }

    #pragma omp parallel for //divides the work of the for-loop among the threads of the current team.
        for(int i = 1; i < 256; i++){
            // Normalization
            equalized[i] = (int)(((float)cumulative_histogram[i] - hist[0])/((float)cols * rows - 1)*255);
        }
}