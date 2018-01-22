

/*
 *    AsTeRICS - Assistive Technology Rapid Integration and Construction Set
 * 
 * 
 *        d8888      88888888888       8888888b.  8888888 .d8888b.   .d8888b. 
 *       d88888          888           888   Y88b   888  d88P  Y88b d88P  Y88b
 *      d88P888          888           888    888   888  888    888 Y88b.     
 *     d88P 888 .d8888b  888   .d88b.  888   d88P   888  888         "Y888b.  
 *    d88P  888 88K      888  d8P  Y8b 8888888P"    888  888            "Y88b.
 *   d88P   888 "Y8888b. 888  88888888 888 T88b     888  888    888       "888
 *  d8888888888      X88 888  Y8b.     888  T88b    888  Y88b  d88P Y88b  d88P
 * d88P     888  88888P' 888   "Y8888  888   T88b 8888888 "Y8888P"   "Y8888P" 
 *
 *
 *                    homepage: http://www.asterics.org 
 *
 *         This project has been funded by the European Commission, 
 *                      Grant Agreement Number 247730
 *  
 *  
 *         Dual License: MIT or GPL v3.0 with "CLASSPATH" exception
 *         (please refer to the folder LICENSE)
 * 
 */

package eu.asterics.component.sensor.realsensegestures;


import java.util.logging.Logger;
import eu.asterics.mw.data.ConversionUtils;
import eu.asterics.mw.model.runtime.AbstractRuntimeComponentInstance;
import eu.asterics.mw.model.runtime.IRuntimeInputPort;
import eu.asterics.mw.model.runtime.IRuntimeOutputPort;
import eu.asterics.mw.model.runtime.IRuntimeEventListenerPort;
import eu.asterics.mw.model.runtime.IRuntimeEventTriggererPort;
import eu.asterics.mw.model.runtime.impl.DefaultRuntimeOutputPort;
import eu.asterics.mw.model.runtime.impl.DefaultRuntimeInputPort;
import eu.asterics.mw.model.runtime.impl.DefaultRuntimeEventTriggererPort;
import eu.asterics.mw.services.AstericsErrorHandling;
import eu.asterics.mw.services.AREServices;

/**
 * 
 * This module picks up extended fingers of a hand in front of an Intel RealSense camera
 * There's not a lot of functionality here yet but it's a good platform for expanding
 * upon RealSense and OpenCV technology
 *  
 * @author Leonhard Hauptfeld [leonhard.hauptfeld@gmail.com]
 *         Date: 
 */
public class RealSenseGesturesInstance extends AbstractRuntimeComponentInstance
{
	final IRuntimeOutputPort opDetected = new DefaultRuntimeOutputPort();
	final IRuntimeOutputPort opExtended = new DefaultRuntimeOutputPort();
	// Usage of an output port e.g.: opMyOutPort.sendData(ConversionUtils.intToBytes(10)); 

	//final IRuntimeEventTriggererPort etpGestureLeft = new DefaultRuntimeEventTriggererPort();
	//final IRuntimeEventTriggererPort etpGestureRight = new DefaultRuntimeEventTriggererPort();
	// Usage of an event trigger port e.g.: etpMyEtPort.raiseEvent();

	//int propGestureLeftCertainty = 50;
	//int propGestureRightCertainty = 50;

	// declare member variables here
    // ok, will do

    private RealSenseNativeConnector native_conn;

    
   /**
    * The class constructor.
    */
    public RealSenseGesturesInstance()
    {
        //opDetected.sendData(new byte[]{1});
        opDetected.sendData(ConversionUtils.booleanToBytes(true));
        opExtended.sendData(ConversionUtils.intToBytes(0));
        // initialize the native connector
        native_conn = new RealSenseNativeConnector(opExtended);
    }

   /**prett
    * returns an Input Port.
    * @param portID   the name of the port
    * @return         the input port or null if not found
    */
    public IRuntimeInputPort getInputPort(String portID)
    {
		return null;
	}

    /**
     * returns an Output Port.
     * @param portID   the name of the port
     * @return         the output port or null if not found
     */
    public IRuntimeOutputPort getOutputPort(String portID)
	{

		if ("detected".equalsIgnoreCase(portID))
		{
			return opDetected;
		}else if ("fingers_detected".equalsIgnoreCase(portID))
        {
            return opExtended;
        }

		return null;
	}

    /**
     * returns an Event Listener Port.
     * @param eventPortID   the name of the port
     * @return         the EventListener port or null if not found
     */
    public IRuntimeEventListenerPort getEventListenerPort(String eventPortID)
    {

        return null;
    }

    /**
     * returns an Event Triggerer Port.
     * @param eventPortID   the name of the port
     * @return         the EventTriggerer port or null if not found
     */
    public IRuntimeEventTriggererPort getEventTriggererPort(String eventPortID)
    {
		/*if ("gestureLeft".equalsIgnoreCase(eventPortID))
		{
			return etpGestureLeft;
		}
		if ("gestureRight".equalsIgnoreCase(eventPortID))
		{
			return etpGestureRight;
		}*/

        return null;
    }
		
    /**
     * returns the value of the given property.
     * @param propertyName   the name of the property
     * @return               the property value or null if not found
     */
    public Object getRuntimePropertyValue(String propertyName)
    {
		/*if ("gestureLeftCertainty".equalsIgnoreCase(propertyName))
		{
			return propGestureLeftCertainty;
		}
		if ("gestureRightCertainty".equalsIgnoreCase(propertyName))
		{
			return propGestureRightCertainty;
		}*/

        return null;
    }

    /**
     * sets a new value for the given property.
     * @param propertyName   the name of the property
     * @param newValue       the desired property value or null if not found
     */
    public Object setRuntimePropertyValue(String propertyName, Object newValue)
    {
		/*if ("gestureLeftCertainty".equalsIgnoreCase(propertyName))
		{
			final Object oldValue = propGestureLeftCertainty;
			propGestureLeftCertainty = Integer.parseInt(newValue.toString());
			return oldValue;
		}
		if ("gestureRightCertainty".equalsIgnoreCase(propertyName))
		{
			final Object oldValue = propGestureRightCertainty;
			propGestureRightCertainty = Integer.parseInt(newValue.toString());
			return oldValue;
		}*/

        return null;
    }

     /**
      * Input Ports for receiving values.
      */


     /**
      * Event Listerner Ports.
      */

	

     /**
      * called when model is started.
      */
      @Override
      public void start()
      {
          super.start();
          System.out.println("Starting gesture tracking");
          native_conn.startRecognitionThread();
      }

     /**
      * called when model is paused.
      */
      @Override
      public void pause()
      {
          super.pause();
          System.out.println("Stopping gesture tracking");
          native_conn.stopRecognitionThread();
      }

     /**
      * called when model is resumed.
      */
      @Override
      public void resume()
      {
          super.resume();
          System.out.println("Starting gesture tracking");
          native_conn.pauseRecognitionThread();
      }

     /**
      * called when model is stopped.
      */
      @Override
      public void stop()
      {
          super.stop();
          System.out.println("Stopping gesture tracking");
          native_conn.resumeRecognitionThread();
      }
}