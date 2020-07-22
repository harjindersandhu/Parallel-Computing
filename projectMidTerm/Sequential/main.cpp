#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <sys/time.h>
#include "hist_maker.cpp"
#include "cumulative_histogram.cpp"
#include "equalize.cpp"

using namespace std;
using namespace cv;

int main(){

    // Image reading
    Mat image = imread("../img/smoke500.jpg", IMREAD_COLOR);

    //We used these functions to varying the size of the same size
    //resize(image, image, Size(10000, 10000));
    //imshow("Original Image", image);

    struct timeval start, end;
    gettimeofday(&start, NULL);

    // For big image dimension is required to allocate array for avoid the segmentation fault
    int *yuv_vector = new int[image.rows * image.cols * 3];

    // Making the histogram
    int histogram[256];
    hist_maker(image, histogram, yuv_vector);

    // Making the equalized histogram
    int equalized[256];

    cumulative_histogram(histogram,equalized, image.cols, image.rows);

    equalize(image, equalized, yuv_vector);

    gettimeofday(&end, NULL);
    double elapsed_time = ((end.tv_sec  - start.tv_sec) * 1000000u + end.tv_usec - start.tv_usec) / 1.e6;
    cout << elapsed_time << endl;

    // Display equalized image
    //resize(image, image, Size(500,500));
    //imshow("Equalized Image", image);

    imwrite("/Users/sandhuharjinder/CLionProjects/Sequential_Version/eq_img/smoke500.jpg", image);
    //waitKey(0);
    return 0;

}
