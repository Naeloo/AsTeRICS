//
// Created by Leonhard on 15.01.2018.
//

#ifndef REALSENSE_GESTURES_NATIVE_HELPERS_H
#define REALSENSE_GESTURES_NATIVE_HELPERS_H


#include <opencv2/opencv.hpp>
#include <algorithm>

namespace cv {
    double median( cv::Mat );
    Point struct_to_point( CvPoint* );
}

namespace math_helpers {
    double to_radians(double);
    double angle_radians(cv::Point, cv::Point);
}

#endif //REALSENSE_GESTURES_NATIVE_HELPERS_H
