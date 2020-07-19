using namespace std;
using namespace cv;

void global_histogram(int histogram[], int equalized[], int cols, int rows)
// compute the cdf(named cumulative_histogram) and normalize it (named equalized)
{
    int global_histogram[256];

    global_histogram[0] = histogram[0];

    for(int i = 1; i < 256; i++)
    {
        global_histogram[i] = histogram[i] + global_histogram[i-1];
        equalized[i] = (int)(((float)global_histogram[i] - histogram[0])/((float)cols * rows - 1)*255);
    }
}
