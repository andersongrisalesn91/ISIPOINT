package android_serialport_api;

import android.os.Handler;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Soft bar code controller
 * You can use any methods you want to make your scanner work or stop.
 * @author YJJ
 */
@SuppressWarnings("ALL")
public class SoftBarCodeAPI {
    private byte[] startCode = { (byte) 0xca, (byte) 0xdf, (byte) 0x02,
            (byte) 0x36, (byte) 0x01, (byte) 0xe3 };
    private byte[] stopCode = { (byte) 0xca, (byte) 0xdf, (byte) 0x02,
            (byte) 0x36, (byte) 0x02, (byte) 0xe3 };
    private byte[] buffer;
    private boolean isScanning;
    private Handler mhandler;
    private ExecutorService Executor = Executors.newSingleThreadExecutor();
    private OnBarCodeListener listener;
    private String data="";
    private final static long TIME_OUT = 8000;
    private final static long TIME_OUT_SHORT = 100;
    public SoftBarCodeAPI(OnBarCodeListener listener) {
        mhandler = new Handler();
        this.listener = listener;
    }

    /**
     *  power on scanner stm32
     */
    public void upGpio() {
        SerialPortManager.getInstance().openSerialPortPrinter();
    }

    /**
     * start scan
     */
    public void startScanner() {
        isScanning = true;
        Executor.execute(sendStart);
        Executor.execute(getdata);
    }
    
    /**
     * stop scan
     */
    public void stopScanner() {
        isScanning = false;
        mhandler.removeCallbacks(getdata);
        Executor.execute(sendStop);
    }
    
    /**
     *  power off scanner
     */
    public void downGpio() {
        SerialPortManager.getInstance().closeSerialPort(4);
    }

    private Runnable sendStart = new Runnable() {

        @Override
        public void run() {
            SerialPortManager.getInstance().write(startCode);
        }
    };
    
    private Runnable sendStop = new Runnable() {
        @Override
        public void run() {
            SerialPortManager.getInstance().write(stopCode);
        }
    };
    private long proTime;
    /**
     * read barcode data
     * when continues scan code,every second read again.
     * if scan none,scanner will close,but it will restart after a litter second.
     */
    private Runnable getdata = new Runnable() {
        long spaceTime = 0;
        @Override
        public void run() {
            proTime = 0;
            proTime = System.currentTimeMillis();
            while (isScanning) {
                int length = 0;
                do{
                    buffer = null;
                    buffer = new byte[1024*50];
                    spaceTime = System.currentTimeMillis()-proTime;//a timer
                    length = SerialPortManager.getInstance().read(buffer, 500,50);
                }while(isScanning&&length==0&&spaceTime<=TIME_OUT);
                if (length > 0) {//have read data.
                    byte[] getData = new byte[length];
                    System.arraycopy(buffer, 0, getData, 0, length);
                    if(isZero(getData)){
                        Log.d("jokey", "isZero");
                        continue;
                    }
                    isScanning = false;//stop scan
                    try {
                        Log.d("jokey", "getData==  "+ DataUtils.toHexString(getData));
                        data = new String(getData,"GBK");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("jokey", "data = "+data);
                            listener.scanCodeSuccess(data);//callback data
                        }
                    });
                }else { //scan no data and time out,without user's operate
                    stopScanner();
                    listener.scanCodeFail();
                    mhandler.postDelayed(new Runnable() {
                        /**
                         * When time out,we will restart scanner after seconds.But not a litter,because stopping and starting scanner need time. 
                         */
                        @Override
                        public void run() {
                            if(ScanSerice.isPressed()|| ScanSerice.isLoop()){
                                startScanner();
                            }else{
                                stopScanner();
                            }
                        }
                    }, TIME_OUT_SHORT);
                }
                
            }
            
        }
    };
    /**
     * 判断数组内容是否全为00
     * @param data
     * @return
     */
    private boolean isZero(byte[] data){
        for (int i = 0; i < data.length; i++) {
            if(data[i]!=0x00){
                return false;
            }
        }
        return true;
    }
    public interface OnBarCodeListener {
        void scanCodeFail();

        void scanCodeSuccess(String data);
    }
}
