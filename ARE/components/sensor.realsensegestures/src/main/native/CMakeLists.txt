cmake_minimum_required(VERSION 3.6.3)
project(realsense-gestures-native)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}")

set(SOURCES library.cpp include/library.h recognizer.cpp include/recognizer.h visualizer.cpp include/visualizer.h helpers.cpp include/helpers.h include/eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector.h jni_methods.cpp include/jni_methods.h)

add_library(realsense-gestures-native SHARED ${SOURCES})
#add_library(realsense-gestures-native32 SHARED ${SOURCES})
#set_target_properties(realsense-gestures-native32 PROPERTIES COMPILE_FLAGS "-m32" LINK_FLAGS "-m32")
add_executable(realsense-gestures-native-test test/main.cpp ${SOURCES})
#add_library(realsense-gestures-native SHARED library.cpp recognizer.cpp visualizer.cpp helpers.cpp jni_methods.cpp)

set_target_properties(realsense-gestures-native PROPERTIES COMPILE_FLAGS " -DINCLUDE_JNI=1")

if(WIN32)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -D_CRT_SECURE_NO_WARNINGS -DWIN32_LEAN_AND_MEAN=1 -static -static-libstdc++")
    # WINDOWS LINKING
    MESSAGE("LINKING WINDOWS DLLS")
    #set(LIBS "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/realsense2.dll" "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_core.dll" "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_highgui.dll" "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_imgproc.dll")
    set(LIBS "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86/realsense2.dll" "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86/opencv_core.dll" "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86/opencv_highgui.dll" "${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86/opencv_imgproc.dll")
    #link_libraries("${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_core.dll")
    #link_libraries("${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_highgui.dll")
    #link_libraries("${CMAKE_CURRENT_SOURCE_DIR}/../resources/lib/native/windows/x86-64/opencv_imgproc.dll")
    # WINDOWS INCLUDE DIRS (Might differ for you! Didn't set up ld in MinGW because it's a PITA)
    set(REALSENSE_INCLUDE_DIR   "C:/MinGWLibs/librealsense/include")
    set(OPENCV_INCLUDE_DIR      "C:/MinGWLibs/opencv-3.4.0/build/install/include")
    set(JDK_INCLUDE_DIR         "C:/Program Files/Java/jdk1.8.0_121/include")
    # JDK OS SPECIFIC INCLUDE
    include_directories("${JDK_INCLUDE_DIR}/win32")
elseif(UNIX)
    # LINUX LINKING
    MESSAGE("LINKING LINUX LIBRARIES")
    set(LIBS realsense2 opencv_core opencv_highgui opencv_imgproc)
    #link_libraries(realsense2)
    #link_libraries(opencv_core)
    #link_libraries(opencv_highgui)
    #link_libraries(opencv_imgproc)
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

target_link_libraries(realsense-gestures-native ${LIBS})
target_link_libraries(realsense-gestures-native-test ${LIBS})
#target_link_libraries(realsense-gestures-native32 ${LIBS32})
include_directories(include ${REALSENSE_INCLUDE_DIR} ${OPENCV_INCLUDE_DIR} ${JDK_INCLUDE_DIR})