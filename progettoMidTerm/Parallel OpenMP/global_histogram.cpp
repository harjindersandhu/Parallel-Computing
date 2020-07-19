void global_histogram(int histogram[], int equalized[], int cols, int rows)
// compute the cdf(named global_histogram) and normalize it (named equalized)
{

    int global_histogram[256];

    global_histogram[0] = histogram[0];

    // Cumulative_histogram needs to be compute in sequential mode
    for(int i = 1; i < 256; i++)
    {
        global_histogram[i] = histogram[i] + global_histogram[i-1];
    }

#pragma omp parallel for
    for(int i = 1; i < 256; i++){
        equalized[i] = (int)(((float)global_histogram[i] - histogram[0])/((float)cols * rows - 1)*255);
    }
}

