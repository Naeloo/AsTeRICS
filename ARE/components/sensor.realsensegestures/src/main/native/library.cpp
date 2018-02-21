#include "library.h"

bool isRecognizing = false;
visualizer* gesture_visualizer = nullptr;

void set_recognizing(bool recog) {
    isRecognizing = recog;
}

void loop() { loop( false); }
void loop(bool use_visualizer) {
    //visualizer gesture_visualizer((char *) "Gesture Recognition");
    recognizer gesture_recognizer;
    hand_model* recognized_model;

    if(use_visualizer){
        gesture_visualizer = new visualizer((char*)"Gesture Recognition");
    }

    // Declare depth colorizer for pretty visualization of depth data
    rs2::colorizer color_map;
    // Use color scheme option 2 (grayscale, distant black, close white)
    color_map.set_option(RS2_OPTION_COLOR_SCHEME, 2);

    // Declare RealSense pipeline, encapsulating the actual device and sensors
    rs2::pipeline pipe;
    // Start streaming with default recommended configuration
    pipe.start();

    isRecognizing = true;
    int previousFingers = 0;

    using namespace cv;
    std::cout << "Using OpenCV version " + std::to_string(CV_MAJOR_VERSION) << std::endl;
    while ( isRecognizing)
    {
        if(use_visualizer) { waitKey(1); }
        rs2::frameset data = pipe.wait_for_frames(); // Wait for next set of frames from the camera
        rs2::frame depth = color_map(data.get_depth_frame());
        //rs2::frame depth = data.get_depth_frame();

        // Query frame size (width and height)
        const int w = depth.as<rs2::video_frame>().get_width();
        const int h = depth.as<rs2::video_frame>().get_height();

        // Create OpenCV matrix of size (w,h) from the colorized depth data
        Mat image(Size(w, h), CV_8UC3, (void*)depth.get_data(), Mat::AUTO_STEP);

        recognized_model = gesture_recognizer.get_hand_model(image);

        #ifdef INCLUDE_JNI
        if(previousFingers != recognized_model->num_fingers){
            report_fingers(recognized_model->num_fingers);
            previousFingers = recognized_model->num_fingers;
        }
        #endif
        // If the visualizer exists, display frame

        if(gesture_visualizer != nullptr) {
            gesture_visualizer->display_data(recognized_model->display_frame, recognized_model);
        }


        // i have come here to chew bubblegum and garbage collect
        // and I'm all out of gum
        delete recognized_model;
    }
    delete gesture_visualizer;

}

