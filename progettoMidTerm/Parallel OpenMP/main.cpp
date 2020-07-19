#include <omp.h>
#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/imgcodecs/imgcodecs.hpp>
#include "create_histogram.cpp"
#include "global_histogram.cpp"
#include "equalize.cpp"


using namespace std;
using namespace cv;
int main(){

    //For change the threads number
    //omp_set_dynamic(0);     // Explicitly disable dynamic teams
    omp_set_num_threads(8); // Use 4 threads for all consecutive parallel regions

    // Load the image
    Mat image = imread("../img/smoke10000.jpg");
    //resize(image, image, Size(7000,7000));
    //imshow("Original Image", image);

    double start = omp_get_wtime();

    int *yuv_vector = new int[image.rows*image.cols * 3];

    // Generate the histogram
    int histogram[256];
    create_histogram(image, histogram, yuv_vector);

    // Generate the equalized histogram
    int equalized[256];

    global_histogram(histogram,equalized, image.cols, image.rows);

    equalize(image, equalized, yuv_vector);

    double end = omp_get_wtime();

    cout << end-start << endl;

    // Display equalized image
    //resize(image, image, Size(600,450));
    //imshow("Equalized Image",image);

    imwrite("/Users/sandhuharjinder/CLionProjects/Parallel_OMP_Version/eq_img/smoke10000.jpg", image);
    //waitKey(0);

    return 0;
}
