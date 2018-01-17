//
// Created by Leonhard on 15.01.2018.
//

#include <opencv2/opencv.hpp>
#include "helpers.h"

double cv::median(cv::Mat channel) {
    /*channel = channel.reshape(0,1); // spread Input Mat to single row
    std::vector<double> vecFromMat;
    channel.copyTo(vecFromMat); // Copy Input Mat to vector vecFromMat
    std::nth_element(vecFromMat.begin(), vecFromMat.begin() + vecFromMat.size() / 2, vecFromMat.end());
    return vecFromMat[vecFromMat.size() / 2];*/
    // calculates the median value of a single channel
    // based on https://github.com/arnaudgelas/OpenCVExamples/blob/master/cvMat/Statistics/Median/Median.cpp
    double m = (channel.rows*channel.cols) / 2;
    int bin = 0;
    double med = -1.0;

    int histSize = 256;
    float range[] = { 0, 256 };
    const float* histRange = { range };
    bool uniform = true;
    bool accumulate = false;
    cv::Mat hist;
    cv::calcHist( &channel, 1, 0, cv::Mat(), hist, 1, &histSize, &histRange, uniform, accumulate );

    for ( int i = 0; i < histSize && med < 0.0; ++i )
    {
        bin += cvRound( hist.at< float >( i ) );
        if ( bin > m && med < 0.0 )
            med = i;
    }

    return med;
}

//cv::Point cv::struct_to_point(CvPoint* cv_struct) {
//return Point(cv_struct->x, cv_struct->y);
//}

double math_helpers::to_radians(double degrees) {
    return degrees / 180 * CV_PI;
}

double math_helpers::angle_radians(cv::Point p1, cv::Point p2) {
    double dot = p1.dot(p2);
    double magnitA = sqrt(pow(p1.x, 2) + pow(p2.y, 2));
    double magnitB = sqrt(pow(p1.x, 2) + pow(p2.y, 2));

    double agl = acos(dot / (magnitA * magnitB));
    //std::cout << std::to_string(agl) << std::endl;
    return agl;
}