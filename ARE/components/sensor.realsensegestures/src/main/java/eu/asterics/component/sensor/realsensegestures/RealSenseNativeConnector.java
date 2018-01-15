package eu.asterics.component.sensor.realsensegestures;

import eu.asterics.mw.model.runtime.IRuntimeOutputPort;

import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * Created by Leonhard on 04.01.2018.
 */
public class RealSenseNativeConnector {
    private Calendar lastUpdated = Calendar.getInstance();
    private int currentFingerNo = 0;
    private IRuntimeOutputPort finger_port;

    public RealSenseNativeConnector(IRuntimeOutputPort finger_port){
        // Maybe put a nice painting in here so it's not so empty
        this.finger_port = finger_port;
    }

    // Callback called from C++ code when extended finger numbers change
    public void fingerNumberChanged(int fingersExtended){
        //System.out.println("FINGERS: " + fingersExtended);
        this.finger_port.sendData(ByteBuffer.allocate(4).putInt(fingersExtended).array());
    }
    // Native methods for recognition control
    public native void start_recognition();
    public native void pause_recognition();
    public native void stop_recognition();
}
