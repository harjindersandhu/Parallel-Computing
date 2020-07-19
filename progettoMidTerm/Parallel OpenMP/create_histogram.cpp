#include <omp.h>
#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/imgcodecs/imgcodecs.hpp>

using namespace std;
using namespace cv;

void create_histogram(Mat image, int histogram[], int *yuv_vector)
// make the histogram and convert from RGB to YUV creating a coloumn vector yuv_vector with col*row*3 dimension
{

    // initialize all intensity values to 0
    for (int i = 0; i < 256; i++) {
        histogram[i] = 0;
    }


#pragma omp parallel default(shared)
    // all variables are shared with all threads
    {
#pragma omp for schedule(static)
        // with static, When k>n, threads are assigned to k/n chunks of iteration space
        // where k = number of iterations; n = number of threads
        {
            for (int i = 0; i < image.rows; i++) {
                for (int j = 0; j < image.cols; j++) {

                    Vec3b intensity = image.at<Vec3b>(i, j);

                    int R = intensity.val[0]; // R is red
                    int G = intensity.val[1]; // G is green
                    int B = intensity.val[2]; // B is blue

                    // Convert from RGB into YUV
                    int Y = R * .299000 + G * .587000 + B * .114000; // Y is the luminous level
                    int U = R * -.168736 + G * -.331264 + B * .500000 + 128;
                    int V = R * .500000 + G * -.418688 + B * -.081312 + 128;

                    histogram[Y]++;

                    int index = (i * image.cols + j) * 3;

                    // memorize in a column vector all the pixels of the image in RGB format ( the yuv_vector is initialized like rows*cols*3 )
                    yuv_vector[index] = Y;
                    yuv_vector[index + 1] = U;
                    yuv_vector[index + 2] = V;

                }
            }
        }
    }
}

