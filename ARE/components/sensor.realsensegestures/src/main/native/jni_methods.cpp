//
// Created by Leonhard on 21.02.2018.
//
#ifdef INCLUDE_JNI

#include "jni_methods.h"

// REPORTING HELPERS

JNIEnv* jenv;
jobject jNativeConnector;
jmethodID fingerCallback;

void report_fingers (int num_fingers) {
    if(jenv != nullptr) {
        jenv->CallVoidMethod(jNativeConnector, fingerCallback, num_fingers);
    }
}

// RECOGNITION
// Native method to START RECOGNITION
JNIEXPORT void JNICALL Java_eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector_start_1recognition
(JNIEnv * env, jobject nativeConnector){
// Start the loop

//env = jenv;
//nativeConnector = jnativeConnector;
    // JNI interfacing
    //jclass nativeConnectorClass = env->FindClass("eu/asterics/component/sensor/realsensegestures/RealSenseNativeConnector");

    jenv = env;
    jNativeConnector = nativeConnector;
    fingerCallback = env->GetMethodID(env->GetObjectClass(nativeConnector),  "fingerNumberChanged", "(I)V");

    loop(false);
}
// Native method to PAUSE RECOGNITION
JNIEXPORT void JNICALL Java_eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector_pause_1recognition
(JNIEnv * env, jobject nativeConnector){
// Stop the loop
set_recognizing(false);
}
// Native method to STOP RECOGNITION
JNIEXPORT void JNICALL Java_eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector_stop_1recognition
(JNIEnv * env, jobject nativeConnector){
// Stop the loop
set_recognizing(false);
}

// VISUALIZATION
// TODO: Find some way of visualization that works with JNI

JNIEXPORT void JNICALL Java_eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector_start_1visualization
(JNIEnv *, jobject){
//gesture_visualizer = new visualizer((char *) "Gesture Recognition");
}

JNIEXPORT void JNICALL Java_eu_asterics_component_sensor_realsensegestures_RealSenseNativeConnector_stop_1visualization
(JNIEnv *, jobject){
//
}

#endif