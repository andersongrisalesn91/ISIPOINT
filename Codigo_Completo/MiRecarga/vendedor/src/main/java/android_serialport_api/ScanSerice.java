package android_serialport_api;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sim.scanner.KeyWatcher;

import co.com.mirecarga.vendedor.R;
import android_serialport_api.SoftBarCodeAPI.OnBarCodeListener;
import wise.core.CoreWiseInputManager;

/**
 * Scan barcode service
 * This is a important service that be used to service your soft decode scanner.
 * You can press side key to scan bar code ,and out put result at any cursor position when this service is working.
 * This service is auto-boot in background when your device is boot,so that your scanner work without error.
 * @author YJJ
 */
@SuppressWarnings("ALL")
public class ScanSerice extends Service implements IBroadcastAction,OnBarCodeListener{
    // add by yjj 2016/11/14
    private MediaPlayer mp;
    //end
    private ScanerBroadcast receiver;
    private SoftBarCodeAPI api;
    private Handler mHandler = new MSGHandler();
    private KeyWatcher watcher = KeyWatcher.getInstance(mHandler);
    public static final int MSG_BEGIN_SCAN = 1;
    public static final int MSG_CANCEL_SCAN = 2;
    //side button pressed
    private static boolean isPressed = false;
    private static boolean isLoop = false;
    /**
     * Other function control the stm32 to close or open.
     * When Other function close stm32,w = 0.
     * After that,power on scanner should waits 1s to make sure that scanner can stop normally when you loosen the side key.
     * Just first.
     * This flag is to solve the problem that the scanner cann't stop at once when user press side key quickly.
     */
    public static int w = 0;
    /**
     * When reopen screen and open scanner,wait 500ms before stop scanner.
     * Just first.
     * This flag is to solve the problem that the scanner cann't stop at once when user press side key quickly.
     */
    private int t = 0;
    /**
     * Whether stm32 is power on
     */
    public static boolean isUp = false;
    //screen status
    public static boolean screenStatus = true;
    
    public static boolean isPressed() {
        return isPressed;
    }
    public void setPressed(boolean isPressed) {
        ScanSerice.isPressed = isPressed;
    }
    public static boolean isLoop() {
        return isLoop;
    }
    public void setLoop(boolean isLoop) {
        ScanSerice.isLoop = isLoop;
    }
    private void waitTime(final long time){
        try {
            Thread.sleep(time);//After power on scanner,we should wait 1000ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private class MSGHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_BEGIN_SCAN:
                pressDown();
                break;
            case MSG_CANCEL_SCAN:
                pressUp();
                break;
            }
        }
    }
    private void pressDown(){
        if(!PrinterAPI.isFlag()){
            if(screenStatus){
                setPressed(true);
                Log.d("jokey", "begin scan!");
                w++;
                t++;
                if(w>=Integer.MAX_VALUE||t>=Integer.MAX_VALUE){
                    w = 2;
                    t = 2;
                }
                switch (SerialPortManager.serialPortStatus) {
                case SerialPortManager.CLOSE:
                    Log.d("jokey", "SerialPortManager.CLOSE");
                    api.upGpio();
                    waitTime(1000);
                    break;
                case SerialPortManager.STM32:
                    break;
                default:
                    Log.d("jokey", "SerialPortManager.Other");
                    return;
                }
                Log.d("jokey", "isPressed"+isPressed());
                if(isPressed()){
                    api.startScanner();
                    Log.d("jokey", "startScanner!");
                }else{
                    api.stopScanner();
                    Log.d("jokey", "BEGIN--->cancel_scan");
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "The printer is working", Toast.LENGTH_SHORT).show();
        }
    }
    private void pressUp(){
        if(!PrinterAPI.isFlag()){
            Log.d("jokey", "w = :"+w);
            if(w == 1){
                waitTime(1000);
            }
            if(screenStatus){
                setPressed(false);
                Log.d("jokey", "t = :"+t);
                if(t==1){
                    waitTime(500);
                }
                switch (SerialPortManager.serialPortStatus) {
                    case SerialPortManager.CLOSE:
                        break;
                    case SerialPortManager.STM32:
                        api.stopScanner();
                        Log.d("jokey", "CANCEL--->cancel_scan");
                        break;
                    default:
                        break;
                }
                
            }
        }
    }
    private class ScanerBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(IBroadcastAction.ACTION_START_DECODE.equals(intent.getAction())){
                setLoop(intent.getBooleanExtra("isLoop", false));
                api.startScanner();
            }else if(IBroadcastAction.ACTION_OPEN_SCAN.equals(intent.getAction())){
                if(!SerialPortManager.getInstance().isPrintOpen()){
                    api.upGpio();
                }
            }else if(IBroadcastAction.ACTION_CLOSE_SCAN.equals(intent.getAction())){
                if(SerialPortManager.getInstance().isPrintOpen()){
                    w = 0;
                    api.downGpio();
                }
                
            }else if(IBroadcastAction.ACTION_STOP_DECODE.equals(intent.getAction())){
                setLoop(intent.getBooleanExtra("isLoop", false));
                api.stopScanner();
            }else if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
                screenStatus = false;
                if (isUp) {
                    Log.d("jokey", "ACTION_SCREEN_OFF-->isUp："+isUp);
                    api.downGpio();
                    t = 0;
                }
            }else if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
                screenStatus = true;
                if (isUp) {
                    Log.d("jokey", "ACTION_SCREEN_ON-->isUp："+isUp);
                    api.upGpio();
                }
            }else if(Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
                screenStatus = true;
                if (isUp) {
                    Log.d("jokey", "ACTION_USER_PRESENT-->isUp："+isUp);
                    api.upGpio();
                }
            }else if(IBroadcastAction.ACTION_CHECK_SERIAL_PORT.equals(intent.getAction())){
                Log.d("jokey", "ACTION_CHECK_SERIAL_PORT");
            }
        }
    }
    
    @Override
    public void onCreate() {
        Log.d("jokey", "service-->onCreate");
        mp = new MediaPlayer();
        api = new SoftBarCodeAPI(this);
        //regist broadcast
        receiver = new ScanerBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(IBroadcastAction.ACTION_OPEN_SCAN);
        filter.addAction(IBroadcastAction.ACTION_CLOSE_SCAN);
        filter.addAction(IBroadcastAction.ACTION_START_DECODE);
        filter.addAction(IBroadcastAction.ACTION_STOP_DECODE);
        filter.addAction(IBroadcastAction.ACTION_CHECK_SERIAL_PORT);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiver, filter);
        
        if(!watcher.init())
        {
            Log.d("jokey","A catactrophic error,can not init KeyWatcher.");
        }
        watcher._start();
    }
    
    /**
     *  Be killed and restart
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    /**
     *  Be killed and restart
     */
    @Override
    public void onDestroy() {
        mp.release();
        mp = null;
        super.onDestroy();
        if(SerialPortManager.getInstance().isOpen()){
            api.downGpio();
        }
        watcher.release();
        this.startService(new Intent(this, ScanSerice.class));
    }
    
    @Override
    public void scanCodeSuccess(String data) {
        //add by yjj 2016/11/16
        mp.reset();
        mp=MediaPlayer.create(this, R.raw.scan_new);
        mp.start();   
        //end
        autoStartHttp(true,data);
        //input code to any  text box
        CoreWiseInputManager.getInstance(this).sendcode2cursor(data);
        //send broadcast with data
        Intent intent = new Intent(IBroadcastAction.ACTION_DECODE_DATA);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND); 
        intent.putExtra(IBroadcastAction.INTENT_BARCODE_DATA, data);
        sendBroadcast(intent);
        Log.w("jokey", "Time1==  "+System.currentTimeMillis());
    }
    @Override
    public void scanCodeFail() {
        Intent intent = new Intent(IBroadcastAction.TIME_OUT);
        sendBroadcast(intent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /** Scan uri address then auto start system browser
     * @param isAuto whether auto start system browser
     * @param uri uri address
     */
    private void autoStartHttp(boolean isAuto,String uri){
        if(isAuto&&uri.startsWith("http")){
            Log.d("jokey", "http");
            Intent intent = new Intent();        
            intent.setAction(Intent.ACTION_VIEW);    
            Uri content_url = Uri.parse(uri);   
            intent.setData(content_url);  
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
