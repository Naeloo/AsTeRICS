//
// Created by Leonhard on 10.01.2018.
//

#include <opencv2/opencv.hpp>

#ifndef REALSENSE_GESTURES_NATIVE_RECOGNIZER_H
#define REALSENSE_GESTURES_NATIVE_RECOGNIZER_H

struct hand_model{
    int num_fingers;
    std::vector<cv::Point> hand_contour;
    std::vector<int> hand_hull_indexes;
    std::vector<cv::Vec4i> hand_defects;
    std::vector<int> finger_defects_indexes;
    cv::Mat display_frame;
};

class recognizer {
public:
    hand_model* get_hand_model(cv::Mat);
private:
    cv::Mat segment_arm(cv::Mat);
    hand_model* find_defects(cv::Mat);
    void process_hand(hand_model*);
    const int segment_area = 10;
    const int median_tolerance = 50;
    const int cutoff_distance = 150;
    const int maximum_finger_degrees = 95;
};


#endif //REALSENSE_GESTURES_NATIVE_RECOGNIZER_H
