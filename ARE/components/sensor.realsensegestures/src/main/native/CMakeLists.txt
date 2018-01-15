cmake_minimum_required(VERSION 3.9)
project(realsense-gestures-native)

set(CMAKE_CXX_STANDARD 11)
include_directories(include C:/MinGWLibs/librealsense/include C:/MinGWLibs/opencv-3.4.0/build/install/include "C:/Program Files/Java/jdk1.8.0_121/include" "C:/Program Files/Java/jdk1.8.0_121/include/win32")

add_library(realsense-gestures-native SHARED library.cpp include/library.h recognizer.cpp include/recognizer.h visualizer.cpp include/visualizer.h helpers.cpp include/helpers.h include/eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector.h)

if(WIN32)
    # WINDOWS LINKING
    MESSAGE("LINKING WINDOWS DLLS")
    target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_BINARY_DIR}/realsense2.dll")
    target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_BINARY_DIR}/libopencv_core340.dll")
    target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_BINARY_DIR}/libopencv_highgui340.dll")
    target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_BINARY_DIR}/libopencv_imgproc340.dll")
elseif(UNIX)
    # LINUX LINKING
    MESSAGE("LINKING LINUX LIBRARIES")
elseif(APPLE)
    # MAC LINKING
    MESSAGE("LINKING APPLE FRAMEWORKS")
endif(WIN32)

#target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_BINARY_DIR}/libopencv_imgproc340.dll")

add_executable(realsense-gestures-native-test test/main.cpp)
target_link_libraries(realsense-gestures-native-test realsense-gestures-native)