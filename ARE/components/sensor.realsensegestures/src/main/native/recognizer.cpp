//
// Created by Leonhard on 10.01.2018.
//

#include "recognizer.h"
#include "helpers.h"
#include <algorithm>

using namespace cv;
using namespace std;

hand_model* recognizer::get_hand_model(Mat img) {

    // Do the thing with the making single channel grayscale stuffs
    Mat gray_img;
    cvtColor(img, gray_img, CV_BGR2GRAY);

    //std::cout << "Channels: " + std::to_string(gray_img.channels()) << std::endl;
    gray_img = segment_arm(gray_img);
    hand_model* recognized_model = find_defects(gray_img);
    process_hand(recognized_model);

    recognized_model->display_frame = gray_img;
    return recognized_model;
}

Mat recognizer::segment_arm(Mat frame) {
    Size s = frame.size();

    // Cut out the relevant middle segment portion
    Mat center_segment = frame(Rect(
            Point(s.width/2 - segment_area, s.height/2 - segment_area),
            Point(s.width/2 + segment_area, s.height/2 + segment_area)));
    // get the median from that segment
    double med = cv::median(center_segment);
    // find all pixels within median tolerance
    inRange(frame, Scalar(std::min((int)med - median_tolerance, cutoff_distance)), Scalar(med + median_tolerance), frame);
    // fill in gaps that were caused by segments not being inside the tolerance
    morphologyEx(frame, frame, MORPH_CLOSE, Mat::ones(Size(3,3), CV_8UC1));
    // set all the processed pixels to grey
    frame.setTo(Scalar(128), frame);
    // set small center region to grey to ensure that the fill works properly
    rectangle(frame, Point(s.width/2-3, s.height/2-3), Point(s.width/2+3, s.height/2+3), Scalar(128),CV_FILLED);
    // fill the center point white, meaning only the hand + arm will get filled
    floodFill(frame, Point(s.width/2, s.height/2), Scalar(255));
    // apply threshold to cut out all the areas that aren't white (aren't hand/arm)
    threshold(frame, frame, 129,255, THRESH_BINARY);

    return frame;

}

hand_model* recognizer::find_defects(cv::Mat frame) {
    vector<vector<Point> > contours;
    vector<Point> largestContour;
    vector<Vec4i> hierarchy;

    auto * result = new hand_model;
    // find contours in the segmented / processed image
    findContours(frame, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE, Point(0,0));

    double largestArea = 0; unsigned largestIndex = 0;
    for(unsigned i=0; i < contours.size(); i++) {
        double currentArea = contourArea(contours[i]);
        if(currentArea > largestArea){
            largestArea = currentArea;
            largestIndex = i;
        }
    }
    largestContour = contours[largestIndex];
    approxPolyDP(largestContour, largestContour, 5, true);

    vector<int> hull;
    vector<Vec4i> defects;
    convexHull(largestContour, hull, false, false);
    convexityDefects(largestContour, hull, defects);

    //std::cout << std::to_string(defects.size()) + " defects found" << std::endl;

    result->hand_contour = largestContour;
    result->hand_defects = defects;
    result->hand_hull_indexes = hull;

    return result;
}

void recognizer::process_hand(hand_model* model) {
    // If there are less than two defects, there are no extended fingers for sure
    if(model->hand_defects.size() <= 2) {
        model->num_fingers = 0;
        return;
    }
    model->num_fingers = 1;  // Start the count at 1
    // Go through all the detected defects
    for(unsigned c=0; c<model->hand_defects.size();c++){
        cv::Point start = model->hand_contour[model->hand_defects[c][0]];
        cv::Point end = model->hand_contour[model->hand_defects[c][1]];
        cv::Point far = model->hand_contour[model->hand_defects[c][2]];

        double angle = math_helpers::angle_radians(start - far, end - far);
        if(angle < math_helpers::to_radians(maximum_finger_degrees)) {
            //std::cout << std::to_string(angle) << std::endl;
            model->num_fingers += 1;
            model->finger_defects_indexes.push_back(c);
            //std::cout << std::to_string(math_helpers::angle_radians(start - far, end - far)) << std::endl;
        }
    }
    model->num_fingers = min(model->num_fingers, 5);  // A (human) hand has no more than 5 fingers, sorry lizard people
}