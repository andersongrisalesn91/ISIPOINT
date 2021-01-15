package android_serialport_api;

/**
 * 800scan action
 * 
 * @author YJJ
 */
public interface IBroadcastAction {
	/**
	 * power on
	 */
	String ACTION_OPEN_SCAN = "com.jokey.jokey.ACTION_OPEN_SCAN";
	/**
	 * power off
	 */
	String ACTION_CLOSE_SCAN = "com.jokey.jokey.ACTION_CLOSE_SCAN";
	/**
	 * start decode
	 */
	String ACTION_START_DECODE = "com.jokey.jokey.ACTION_START_DECODE";
	/**
	 * stop decode
	 */
	String ACTION_STOP_DECODE = "com.jokey.jokey.ACTION_STOP_DECODE";
	/**
	 * decode data
	 */
	String ACTION_DECODE_DATA = "com.jokey.jokey.ACTION_DECODE_DATA";
	/**
	 * check serial port
	 */
	String ACTION_CHECK_SERIAL_PORT = "com.jokey.jokey.ACTION_CHECK_SERIAL_PORT";
	/**
	 * scan time out
	 */
	String TIME_OUT = "com.jokey.jokey.TIME_OUT";
	String INTENT_BARCODE_DATA = "com.jokey.jokey.barcode_data";
	String ISUP = "isUp";
	
	
}
