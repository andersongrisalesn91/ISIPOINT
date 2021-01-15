package android_serialport_api;

import android.os.SystemClock;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import co.com.mirecarga.core.util.AppLog;

@SuppressWarnings("ALL")
public class SerialPortManager {
    /**
     * El tag para el log.
     */
    private static final String TAG = "SerialPortManager";

    public static boolean switchRFID = false;

    final byte[] UP = { '1' };
    final byte[] DOWN = { '0' };

    private SerialPort mSerialPort = null;

    private boolean isOpen;

    private boolean isPrintOpen;
    private boolean firstOpen = false;

    private OutputStream mOutputStream;

    private InputStream mInputStream;

    private byte[] mBuffer = new byte[50 * 1024];

    private int mCurrentSize = 0;
    private int len = 0;

    private ReadThread mReadThread;
    
    private boolean isStm32 = fileIsExists("/sys/class/stm32_gpio/stm32_en/enable");
    
    public static final int CLOSE = 0;
    public static final int STM32 = 1;
    public static final int IDCARD = 2;
    public static final int ICCARD = 3;
    
    public static int serialPortStatus = CLOSE;
    /**
     * 获取该类的实例对象，为单例
     */
    private volatile static SerialPortManager mSerialPortManager;

    public static SerialPortManager getInstance() {
        if (mSerialPortManager == null) {
            synchronized (SerialPortManager.class) {
                if (mSerialPortManager == null) {
                    mSerialPortManager = new SerialPortManager();
                }
            }
        }
        return mSerialPortManager;
    }

    /**
     * 判断串口是否打开
     * 
     * @return true：打开 false：未打开
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * 判断打印机串口是否打开
     * 
     * @return true：打开 false：未打开
     */
    public boolean isPrintOpen() {
        return isPrintOpen;
    }

    /**
     * 打开串口，3卡，32550,需要等待反馈
     */
    public boolean openSerialPort() {
        if (mSerialPort == null) {

            try {
                mSerialPort = new SerialPort(new File("/dev/ttyHSL1"), 230400, 0);
            } catch (IOException e) {
                AppLog.error(TAG, e, "Error en openSerialPort");
                return false;
            }

            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
            isOpen = true;
            serialPortStatus = ICCARD;
            firstOpen = true;
            return true;
        }
        return false;
    }

    /**
     * 打开串口，打印机+超高频+PSAM+扫码+指纹，stm32
     */
    public boolean openSerialPortPrinter() {
        if (mSerialPort == null) {
            // 上电
            try {
                setUpGpioPrinter();
                Log.d("jokey", "isStm32:  "+isStm32);
                if (isStm32) {
                    Thread.sleep(100);
                    setUpGpioStm32();
                }
                mSerialPort = new SerialPort(new File("/dev/ttyHSL0"), 230400, 0);
            } catch (IOException e) {
                AppLog.error(TAG, e, "Error en openSerialPortPrinter");
                return false;
            } catch (InterruptedException e) {
                AppLog.error(TAG, e, "Error en openSerialPortPrinter InterruptedException");
                return false;
            }

            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
            ScanSerice.isUp = true;
            isOpen = true;
            isPrintOpen = true;
            serialPortStatus = STM32;
            firstOpen = true;
            Log.d("jokey", "openSerialPortPrinter");
            return true;
        }
        return false;
    }

    /**
     * 打开身份证串口
     */
    public boolean openSerialPortIDCard() {
        if (mSerialPort == null) {
            // 上电
            try {
                 setUpGpioIDCard();
                // mSerialPort = new SerialPort(new File("/dev/ttyHSL1"),115200,0);
                 
                mSerialPort = new SerialPort(new File("/dev/ttyHSL1"), 230400, 0);
            } catch (IOException e) {
                AppLog.error(TAG, e, "Error en openSerialPortIDCard");
                return false;
            }

            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
            isOpen = true;
            firstOpen = true;
            serialPortStatus = IDCARD;
            Log.d("jokey", "openSerialPortIDCard");
            return true;
        }
        return false;
    }

    /**
     * 关闭串口，如果不需要读取指纹或身份证信息时，就关闭串口(可以节约电池电量)，建议程序退出时关闭
     */
    public void closeSerialPort(int flag) {
        if (mReadThread != null)
            mReadThread.interrupt();
        mReadThread = null;
        try {
            switch (flag) {
            case 0:
                setDownGpioPrinter();
                setDownGpioIDCard();
                if (isStm32)
                    setDownGpioStm32();
                break;
            case 1:
                break;
            case 2:
                setDownGpioPrinter();
                if (isStm32)
                    setDownGpioStm32();
                ScanSerice.isUp = false;
                ScanSerice.w = 0;
                isPrintOpen = false;
                break;
            case 3:
                setDownGpioIDCard();
                break;
            case 4:
                setDownGpioPrinter();
                isPrintOpen = false;
                break;
            default:
                break;
            }
        } catch (IOException e1) {
            AppLog.error(TAG, e1, "Error en closeSerialPort1 %s", flag);
        }
        if (mSerialPort != null) {
            try {
                mOutputStream.close();
                mInputStream.close();
            } catch (IOException e) {
                AppLog.error(TAG, e, "Error en closeSerialPort %s", flag);
            }
            mSerialPort.close();
            mSerialPort = null;
        }
        isOpen = false;
        firstOpen = false;
        serialPortStatus = CLOSE;
        mCurrentSize = 0;
        switchRFID = false;
        if (looperBuffer != null) {
            looperBuffer = null;
        }
    }

    private void closeSerialPort2() {
        if (mReadThread != null)
            mReadThread.interrupt();
        mReadThread = null;
        if (mSerialPort != null) {
            try {
                mOutputStream.close();
                mInputStream.close();
            } catch (IOException e) {
                AppLog.error(TAG, e, "Error en closeSerialPort2");
            }
            mSerialPort.close();
            mSerialPort = null;
        }
        isOpen = false;
        firstOpen = false;
        serialPortStatus = CLOSE;
        mCurrentSize = 0;
        switchRFID = false;
        if (looperBuffer != null) {
            looperBuffer = null;
        }
    }

    public synchronized int read(byte buffer[], int waittime, int interval) {
        if (!isOpen) {
            return 0;
        }
        int sleepTime = 5;
        int length = waittime / sleepTime;
        boolean shutDown = false;
        for (int i = 0; i < length; i++) {
            if (mCurrentSize == 0) {
                SystemClock.sleep(sleepTime);
                continue;
            } else {
                break;
            }
        }

        if (mCurrentSize > 0) {
            long lastTime = System.currentTimeMillis();
            long currentTime = 0;
            int lastRecSize = 0;
            int currentRecSize = 0;
            while (!shutDown && isOpen) {
                currentTime = System.currentTimeMillis();
                currentRecSize = mCurrentSize;
                if (currentRecSize > lastRecSize) {
                    lastTime = currentTime;
                    lastRecSize = currentRecSize;
                } else if (currentRecSize == lastRecSize && currentTime - lastTime >= interval) {
                    shutDown = true;
                }
            }
            if (mCurrentSize <= buffer.length) {
                System.arraycopy(mBuffer, 0, buffer, 0, mCurrentSize);
            }
        } else {
            SystemClock.sleep(100);
        }
        return mCurrentSize;
    }

    public synchronized int readFixedLength(byte buffer[], int waittime, int requestLength) {
        return readFixedLength(buffer, waittime, requestLength, 15);
    }

    public synchronized int readFixedLength(byte buffer[], int waittime, int requestLength, int interval) {
        if (!isOpen) {
            return 0;
        }
        int sleepTime = 5;
        int length = waittime / sleepTime;
        boolean shutDown = false;
        for (int i = 0; i < length; i++) {
            if (mCurrentSize == 0) {
                SystemClock.sleep(sleepTime);
                continue;
            } else {
                break;
            }
        }

        if (mCurrentSize > 0) {
            long lastTime = System.currentTimeMillis();
            long currentTime = 0;
            int lastRecSize = 0;
            int currentRecSize = 0;
            while (!shutDown && isOpen) {
                if (mCurrentSize == requestLength) {
                    shutDown = true;
                } else {
                    currentTime = System.currentTimeMillis();
                    currentRecSize = mCurrentSize;
                    if (currentRecSize > lastRecSize) {
                        lastTime = currentTime;
                        lastRecSize = currentRecSize;
                    } else if (currentRecSize == lastRecSize && currentTime - lastTime >= interval) {
                        shutDown = true;
                    }
                }
            }

            if (mCurrentSize <= buffer.length) {
                System.arraycopy(mBuffer, 0, buffer, 0, mCurrentSize);
            }
        } else {
            closeSerialPort2();
            SystemClock.sleep(100);
        }
        return mCurrentSize;
    }

    private LooperBuffer looperBuffer;

    public void setLoopBuffer(LooperBuffer looperBuffer) {
        this.looperBuffer = looperBuffer;
    }

    public void clear() {
        this.mBuffer = new byte[50 * 1024];
        len = 0;
    }

    public int getIen() {
        return len;
    }

    private void writeCommand(byte[] data) {
        if (!isOpen) {
            return;
        }
        if (firstOpen) {
            SystemClock.sleep(500);
            firstOpen = false;
        }
        mCurrentSize = 0;
        try {
            mOutputStream.write(data);
        } catch (IOException e) {
            AppLog.error(TAG, e, "Error en writeCommand");
        }
    }

    protected synchronized void clearReceiveData() {
        mCurrentSize = 0;
    }

    public synchronized void write(byte[] data) {
        writeCommand(data);
    }

    private void setUpGpioPrinter() throws IOException {
        byte[] buffer1 = new byte[10];
        FileOutputStream fw = new FileOutputStream("/sys/class/cw_gpios/printer_en/enable");
        FileInputStream fi = new FileInputStream("/sys/class/cw_gpios/printer_en/enable");
        fw.write(UP);
        fi.read(buffer1);
//        Log.d("jokey", "UP-->buffer1==   " + DataUtils.toHexString(buffer1));
        fi.close();
        fw.close();
    }

    private void setDownGpioPrinter() throws IOException {
        byte[] buffer1 = new byte[10];
        FileOutputStream fw = new FileOutputStream("/sys/class/cw_gpios/printer_en/enable");
        FileInputStream fi = new FileInputStream("/sys/class/cw_gpios/printer_en/enable");
        fw.write(DOWN);
        fi.read(buffer1);
//        Log.d("jokey", "setDownGpioPrinter");
        fi.close();
        fw.close();
    }

    public byte[] getBuffer() {
        return mBuffer;
    }

    /**
     * 给身份证模块上电
     */
    public void setUpGpioIDCard() throws IOException {
        FileOutputStream fw = new FileOutputStream("/sys/class/idcard_gpio/idcard_en/enable");
        fw.write(UP);
        fw.close();
    }

    /**
     * 给身份证模块下电
     */
    public void setDownGpioIDCard() throws IOException {
        FileOutputStream fw = new FileOutputStream("/sys/class/idcard_gpio/idcard_en/enable");
        fw.write(DOWN);
        fw.close();
        Log.d("jokey", "setDownGpioIDCard");
    }

    private void setUpGpioStm32() throws IOException {
        FileOutputStream fw = new FileOutputStream("/sys/class/stm32_gpio/stm32_en/enable");
        fw.write(UP);
        fw.close();
    }

    private void setDownGpioStm32() throws IOException {
        FileOutputStream fw = new FileOutputStream("/sys/class/stm32_gpio/stm32_en/enable");
        fw.write(DOWN);
        fw.close();
    }

    public int readGpioIDCardStatus() {
        FileInputStream fr;
        int b = 0;
        try {
            fr = new FileInputStream("/sys/class/idcard_gpio/idcard_en/enable");
            b = fr.read();
            fr.close();
        } catch (Exception e) {
            AppLog.error(TAG, e, "Error en readGpioIDCardStatus");
        }
        return b;
    }

    public synchronized void clearBuffer() {
        mCurrentSize = 0;
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            while (!isInterrupted()) {
                int length = 0;
                try {
                    if (mInputStream == null)
                        return;
                    length = mInputStream.read(buffer);
                    if (length > 0) {
                        if (looperBuffer != null) {
                            byte[] buf = new byte[length];
                            System.arraycopy(buffer, 0, buf, 0, length);
                            looperBuffer.add(buf);
                        }
                        System.arraycopy(buffer, 0, mBuffer, mCurrentSize, length);
                        
//                         Log.d("zzd","length: "+length+" data: "+DataUtils.toHexString(buffer).substring(0,length*2));
//                         //add
//                         byte [] newtest=new byte[length];
//                         System.arraycopy(buffer, 0, newtest, 0, length);
////                         char[] tChars=new char[newtest.length];
//                         String s = "";
//                         for(int i=0;i<newtest.length;i++){
//                             s=(char) newtest[i]+s;
//                         }
////                         String test=String.valueOf(tChars);
//                         Log.d("zzd", s);
//                        // end
                        
                        mCurrentSize += length;
                        len += length;
                        if (mCurrentSize > 50 * 1024 - length) {
                            Log.d("zzd", "clearBuffer");
                            clearBuffer();
                        }
                    }
                } catch (IOException e) {
                    AppLog.error(TAG, e, "Error en ReadThread");
                    return;
                }
            }
        }
    }

    private boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            AppLog.error(TAG, e, "Error en fileIsExists %s", strFile);
            return false;
        }
        return true;
    }
}
