#ifndef NATIVE_LIBRARY_H
#define NATIVE_LIBRARY_H

#include <iostream>
#include <librealsense2/rs.hpp>
#include <opencv2/opencv.hpp>

#include "visualizer.h"

#ifdef INCLUDE_JNI
    #include "jni_methods.h"
#endif

void set_recognizing(bool);

void loop();
void loop(bool);

#endif