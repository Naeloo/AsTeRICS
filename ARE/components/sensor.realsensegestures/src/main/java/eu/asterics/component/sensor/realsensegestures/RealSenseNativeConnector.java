package eu.asterics.component.sensor.realsensegestures;

import eu.asterics.component.sensor.realsensegestures.helpers.RealSenseLibraryResolver;
import eu.asterics.mw.data.ConversionUtils;
import eu.asterics.mw.model.runtime.IRuntimeOutputPort;
import eu.asterics.mw.model.runtime.impl.DefaultRuntimeInputPort;
import eu.asterics.mw.services.AstericsModelExecutionThreadPool;
import eu.asterics.mw.services.AstericsThreadPool;
import sun.misc.ThreadGroupUtils;

import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * Created by Leonhard on 04.01.2018.
 */
public class RealSenseNativeConnector {
    //private Calendar lastUpdated = Calendar.getInstance();
    //private int currentFingerNo = 0;
    private RealSenseGesturesInstance parent;
    private boolean transmitting = true;

    private RealSenseNativeConnectorThread work_thread;

    //static String[] native_libs =
    //        {"libgcc_s_seh-1", "libstdc++-6", "libopencv_core340", "libopencv_imgproc340", "libopencv_imgcodecs340", "libopencv_highgui340", "realsense2", "realsense-gestures-native"};

    private RealSenseLibraryResolver libResolver;

    public RealSenseNativeConnector(RealSenseGesturesInstance parent){
        // Assign the finger output port
        this.parent = parent;
        // Load native libs
        libResolver = new RealSenseLibraryResolver();
        libResolver.addLibrary("winpthread-1", RealSenseLibraryResolver.OsType.WINDOWS);
        libResolver.addLibrary("gcc_s_seh-1", RealSenseLibraryResolver.OsType.WINDOWS);
        libResolver.addLibrary("stdc++-6", RealSenseLibraryResolver.OsType.WINDOWS);
        libResolver.addLibrary("opencv_core", RealSenseLibraryResolver.OsType.BOTH, "340");
        libResolver.addLibrary("opencv_imgproc", RealSenseLibraryResolver.OsType.BOTH, "340");
        libResolver.addLibrary("opencv_imgcodecs", RealSenseLibraryResolver.OsType.BOTH, "340");
        libResolver.addLibrary("opencv_highgui", RealSenseLibraryResolver.OsType.BOTH, "340");
        libResolver.addLibrary("realsense2", RealSenseLibraryResolver.OsType.BOTH, "", "");
        libResolver.addLibrary("realsense-gestures-native", RealSenseLibraryResolver.OsType.BOTH);

        libResolver.loadAllLibraries();

        /*for(String lib : native_libs){
            System.out.println("[RealSenseGestures] Load library " + lib);
            System.loadLibrary(lib);
        }
        System.out.println("[RealSenseGestures] All libraries loaded!");*/
    }

    // Callback called from C++ code when extended finger numbers change
    public void fingerNumberChanged(int fingersExtended){
        if(transmitting) {
            AstericsModelExecutionThreadPool.instance.execute(new PassFingersRunnable(fingersExtended));
        }
    }

    // Public methods for doing stuff
    public void startRecognitionThread() {
        startRecognitionThread(0);
    }

    private void startRecognitionThread(int delay) {
        System.out.println("[RealSenseGestures] Starting recognition loop thread");
        // create a new work thread because if it has been aborted before we can't start it again
        work_thread = new RealSenseNativeConnectorThread(this, delay);
        // start the recognition loop in the thread
        AstericsThreadPool.getInstance().execute(work_thread);
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
        startRecognitionThread(2000);
    }

    // Native methods for recognition control
    public native void start_recognition();
    public native void pause_recognition();
    public native void stop_recognition();

    public native void start_visualization();
    public native void stop_visualization();

    public class PassFingersRunnable implements Runnable {
        int extended;
        public PassFingersRunnable(int fingers_extended) {
            this.extended = fingers_extended;
        }

        @Override
        public void run() {
            parent.opExtended.sendData(ConversionUtils.intToBytes(extended));
        }
    }
}
