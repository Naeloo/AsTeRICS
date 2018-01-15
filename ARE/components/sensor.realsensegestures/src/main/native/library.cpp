#include "library.h"

#include <iostream>
#include <librealsense2/rs.hpp>
#include <opencv2/opencv.hpp>

#include "visualizer.h"
#include "eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector.h"

bool isRecognizing = false;

// Native method to START RECOGNITION
JNIEXPORT void JNICALL Java_eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector_start_1recognition
        (JNIEnv * env, jobject){
    // Start the loop
    loop(env);
}
// Native method to PAUSE RECOGNITION
JNIEXPORT void JNICALL Java_eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector_pause_1recognition
        (JNIEnv * env, jobject){
    // Stop the loop
    isRecognizing = false;
}
// Native method to STOP RECOGNITION
JNIEXPORT void JNICALL Java_eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector_stop_1recognition
        (JNIEnv * env, jobject){
    // Stop the loop
    isRecognizing = false;
}

void loop() {
    loop(nullptr);
}
void loop(JNIEnv* env) {
    visualizer gesture_visualizer("Gesture Recognition");
    recognizer gesture_recognizer;

    hand_model* recognized_model;

    // Declare depth colorizer for pretty visualization of depth data
    rs2::colorizer color_map;
    color_map.set_option(RS2_OPTION_COLOR_SCHEME, 2);

    // Declare RealSense pipeline, encapsulating the actual device and sensors
    rs2::pipeline pipe;
    // Start streaming with default recommended configuration
    pipe.start();

    isRecognizing = true;
    int previousFingers = 0;

    using namespace cv;
    while (gesture_visualizer.is_open() && isRecognizing)
    {
        rs2::frameset data = pipe.wait_for_frames(); // Wait for next set of frames from the camera
        rs2::frame depth = color_map(data.get_depth_frame());
        //rs2::frame depth = data.get_depth_frame();

        // Query frame size (width and height)
        const int w = depth.as<rs2::video_frame>().get_width();
        const int h = depth.as<rs2::video_frame>().get_height();

        // Create OpenCV matrix of size (w,h) from the colorized depth data
        Mat image(Size(w, h), CV_8UC3, (void*)depth.get_data(), Mat::AUTO_STEP);

        recognized_model = gesture_recognizer.get_hand_model(image);

        if(env != nullptr && previousFingers != recognized_model->num_fingers){
            // TODO: Should probably move this so the class / method is only loaded once
            jclass theclass = env->FindClass("eu/asterics/component/sensor/realsensegestures");
            jmethodID themethod = env->GetMethodID(theclass, "fingerNumberChanged", "(I)V");
            env->CallVoidMethod(theclass, themethod, recognized_model->num_fingers);
            previousFingers = recognized_model->num_fingers;
        }

        gesture_visualizer.display_data(recognized_model->display_frame, recognized_model);

        // i have come here to chew bubblegum and garbage collect
        // and I'm all out of gum
        delete recognized_model;
    }
}

