package eu.asterics.component.sensor.realsensegestures;

/**
 * Created by leonhard on 1/22/18.
 */
public class RealSenseNativeConnectorThread implements Runnable {
    private RealSenseNativeConnector parent;
    private int startDelay;

    public RealSenseNativeConnectorThread(RealSenseNativeConnector parent) {
        this(parent, 0);
    }
    public RealSenseNativeConnectorThread(RealSenseNativeConnector parent, int startDelay){
        this.parent = parent;
        this.startDelay = startDelay;
    }

    public void run() {
        // Do the blocking recognition loop in thread
        try{
            if(startDelay > 0) { Thread.sleep(startDelay); }

            //this.parent.start_visualization();
            this.parent.start_recognition();
            System.out.println("[RealSenseGestures] Recognition loop stopped - Thread exiting");
        }catch (Exception e){
            System.out.println("[RealSenseGestures] Error in recognition loop - stack trace below");
            e.printStackTrace();
            parent.recognitionThreadExited();
        }
    }
}
