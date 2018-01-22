package eu.asterics.component.sensor.realsensegestures;

import eu.asterics.mw.data.ConversionUtils;
import eu.asterics.mw.model.runtime.IRuntimeOutputPort;

import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * Created by Leonhard on 04.01.2018.
 */
public class RealSenseNativeConnector {
    //private Calendar lastUpdated = Calendar.getInstance();
    //private int currentFingerNo = 0;
    private IRuntimeOutputPort finger_port;
    private boolean transmitting = true;

    private RealSenseNativeConnectorThread work_thread;

    static String[] native_libs =
            {"opencv_core", "opencv_imgproc", "opencv_imgcodecs", "opencv_highgui", "realsense2", "realsense-gestures-native"};

    public RealSenseNativeConnector(IRuntimeOutputPort finger_port){
        // Assign the finger output port
        this.finger_port = finger_port;
        // Load native libs
        for(String lib : native_libs){
            System.out.println("[RealSenseGestures] Load library " + lib);
            System.loadLibrary(lib);
        }
        System.out.println("[RealSenseGestures] All libraries loaded!");
    }

    // Callback called from C++ code when extended finger numbers change
    public void fingerNumberChanged(int fingersExtended){
        System.out.println("[RealSenseGestures] FINGERS: " + fingersExtended);
        if(transmitting) {
            this.finger_port.sendData(ConversionUtils.intToBytes(fingersExtended));
        }
    }

    // Public methods for doing stuff
    public void startRecognitionThread() {
        System.out.println("[RealSenseGestures] Starting recognition loop thread");
        // create a new work thread because if it has been aborted before we can't start it again
        work_thread = new RealSenseNativeConnectorThread(this);
        // start the recognition loop in the thread
        //start_visualization();
        work_thread.start();

    }

    public void stopRecognitionThread() {
        // stop the recognition (hopefully the thread will gracefully exit)
        stop_recognition();
        stop_visualization();
    }

    // These just stop recognition results getting passed through
    // It's a bit hacky but there's no native method for pausing (yet)
    public void pauseRecognitionThread() {
        transmitting = false;
    }

    public void resumeRecognitionThread() {
        transmitting = true;
    }

    public void recognitionThreadExited() {
        System.out.println("[RealSenseGestures] Recognition loop thread exited unexpectedly - attempting restart in 2 seconds");
        work_thread = new RealSenseNativeConnectorThread(this, 2000);
        work_thread.start();
    }

    // Native methods for recognition control
    public native void start_recognition();
    public native void pause_recognition();
    public native void stop_recognition();

    public native void start_visualization();
    public native void stop_visualization();
}
