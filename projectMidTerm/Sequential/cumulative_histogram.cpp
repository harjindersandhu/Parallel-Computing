using namespace std;
using namespace cv;

void cumulative_histogram(int hist[], int equalized[], int cols, int rows)

{
    int cumulative_histogram[256];

    cumulative_histogram[0] = hist[0];

    for(int i = 1; i < 256; i++)
    {
        cumulative_histogram[i] = hist[i] + cumulative_histogram[i-1]; //Computation of cdf

        equalized[i] = (int)(((float)cumulative_histogram[i] - hist[0])/((float)cols * rows - 1)*255); //Normalization of cdf
    }
}
