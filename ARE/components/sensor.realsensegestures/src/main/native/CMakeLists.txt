cmake_minimum_required(VERSION 3.6.3)
project(realsense-gestures-native)

set(CMAKE_CXX_STANDARD 11)

add_library(realsense-gestures-native SHARED library.cpp include/library.h recognizer.cpp include/recognizer.h visualizer.cpp include/visualizer.h helpers.cpp include/helpers.h include/eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector.h)

if(WIN32)
    # WINDOWS LINKING
    MESSAGE("LINKING WINDOWS DLLS")
    target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/realsense2.dll")
    target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_core.dll")
    target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_highgui.dll")
    target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_imgproc.dll")
    # WINDOWS INCLUDE DIRS (Might differ for you! Didn't set up ld in MinGW because it's a PITA)
    set(REALSENSE_INCLUDE_DIR   "C:/MinGWLibs/librealsense/include")
    set(OPENCV_INCLUDE_DIR      "C:/MinGWLibs/opencv-3.4.0/build/install/include")
    set(JDK_INCLUDE_DIR         "C:/Program Files/Java/jdk1.8.0_121/include")
    # JDK OS SPECIFIC INCLUDE
    include_directories("${JDK_INCLUDE_DIR}/win32")
elseif(UNIX)
    # LINUX LINKING
    MESSAGE("LINKING LINUX LIBRARIES")
    target_link_libraries(realsense-gestures-native realsense2)
    target_link_libraries(realsense-gestures-native opencv_core)
    target_link_libraries(realsense-gestures-native opencv_highgui)
    target_link_libraries(realsense-gestures-native opencv_imgproc)
    # UNIX INCLUDE DIRS (These are usually the same if you have the dependencies because yay Unix)
    set(REALSENSE_INCLUDE_DIR   "/home/leonhard/Documents/fh/ba/librealsense_new/include")
    set(OPENCV_INCLUDE_DIR      "/usr/include/opencv2")
    set(JDK_INCLUDE_DIR         "/usr/lib/jvm/java-8-oracle/include")
    # JDK OS SPECIFIC INCLUDE
    include_directories("${JDK_INCLUDE_DIR}/linux")
elseif(APPLE)
    # MAC LINKING
    MESSAGE("LINKING APPLE FRAMEWORKS")
    # i have no idea how this apple stuff works tbh
endif(WIN32)

#target_link_libraries(realsense-gestures-native "${CMAKE_CURRENT_BINARY_DIR}/libopencv_imgproc340.dll")
include_directories(include ${REALSENSE_INCLUDE_DIR} ${OPENCV_INCLUDE_DIR} ${JDK_INCLUDE_DIR})

add_executable(realsense-gestures-native-test test/main.cpp)
target_link_libraries(realsense-gestures-native-test realsense-gestures-native)