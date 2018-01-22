//
// Created by Leonhard on 10.01.2018.
//

#ifndef REALSENSE_GESTURES_NATIVE_VISUALIZER_H
#define REALSENSE_GESTURES_NATIVE_VISUALIZER_H

#include <opencv2/opencv.hpp>
#include "recognizer.h"
#include <thread>

class visualizer {
private:
    char *window_name;
    //std::thread* window_thread;
    //std::vector<cv::Mat> pending_frames;

    //void window_loop();
public:
    visualizer(char*);
    void display_data(cv::Mat, hand_model*);
    bool is_open();
};


#endif //REALSENSE_GESTURES_NATIVE_VISUALIZER_H
