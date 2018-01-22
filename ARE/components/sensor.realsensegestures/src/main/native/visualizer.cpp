//
// Created by Leonhard on 10.01.2018.
//

#include <helpers.h>
#include <jni.h>
#include "visualizer.h"

using namespace cv;

visualizer::visualizer(char* _window_name) {
    window_name = _window_name;
    namedWindow(window_name, WINDOW_AUTOSIZE);
    //waitKey(0);
    //window_thread = new std::thread(&visualizer::window_loop, this);
}

/*void visualizer::window_loop() {

    namedWindow(window_name, WINDOW_AUTOSIZE);
    while(waitKey(1)){
        // Iterate through pending frames
        for(std::vector<cv::Mat>::iterator it = pending_frames.begin(); it != pending_frames.end(); ++it) {
            // Display pending frame
            imshow(window_name, *it);
        }
    }
}*/

void visualizer::display_data(Mat img, hand_model* model) {
    // Get size of image
    Size sz = img.size();
    // Transform back to RGB for RGB drawing stuff
    cvtColor(img, img, COLOR_GRAY2RGB);

    // Draw circle and rectangle in the middle as guide where to put hand
    circle(img, Point(sz.width / 2, sz.height / 2), 5, Scalar(255,0,0) , 3);
    rectangle(img, Point(sz.width / 2 - 10, sz.height / 2 - 10), Point(sz.width/2 + 10, sz.height/2 + 10), Scalar(255,0,0), 3);
    // Display the number of currently detected fingers
    putText(img, std::to_string(model->num_fingers) , Point(50, 50), CV_FONT_NORMAL, 2, Scalar(0,0,255));
    // Draw contours for visualization
    polylines(img, model->hand_contour, true, Scalar(255, 0, 0), 3);
    /*for(unsigned c=0; c<model->hand_hull_indexes.size()-1; c+=2) {
        line(img, model->hand_contour[model->hand_hull_indexes[c]], model->hand_contour[model->hand_hull_indexes[c+1]], Scalar(255,0,255));
    }*/
    //polylines(img, model->hand_hull, true, Scalar(255, 255, 0), 3);
    // Draw all the defects for visualization
    for(unsigned i=0;i<model->hand_defects.size();i++){
        // Draw hull of defect
        line(img, model->hand_contour[model->hand_defects[i][0]] , model->hand_contour[model->hand_defects[i][1]], Scalar(128,0,255), 5);
        // the far point of defect
        if(std::find(model->finger_defects_indexes.begin(), model->finger_defects_indexes.end(), i) != model->finger_defects_indexes.end()) {
            circle(img,model->hand_contour[model->hand_defects[i][2]], 10, Scalar(0, 255, 0), -1);
        } else {
            //circle(img,model->hand_contour[model->hand_defects[i][2]], 5, Scalar(255, 0, 0), -1);
        }

    }
    // Add image to pending queue
    //pending_frames.push_back(img);
    imshow(window_name, img);
    //waitKey(0);
}

bool visualizer::is_open() {
    // I know, I know, but it works
    if(cvGetWindowHandle(window_name)){
        return true;
    }else{
        return false;
    };
}