package android_serialport_api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Handler;
import android.print.Bmp;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.com.mirecarga.core.util.AppLog;

@SuppressWarnings("ALL")
public class PrinterAPI {
    /**
     * El tag para el log.
     */
    private static final String TAG = "PrinterAPI";
    private byte[] print_command = { 0x0A };// 打印命令
    private byte[] initPrinter_command = { 0x1B, 0x40 };// 初始化打印机
    private byte[] set_command = { 0x1B, 0x21, 0x00 };// 设置
    private byte[] setUnderLine_command = { 0x1B, 0x2D, 0x00 };// 无下划线
    private byte[] setAlignType_command = { 0x1B, 0x61, 0x00 };// 默认设置左对齐
    private byte[] printFlashPic_command = { 0x1C, 0x2D, 0x00 };// 打印flash图片
    private byte[] printQrcode_command = { 0x1D, 0x5A, 0x00 };// 默认Qr码
    private byte[] printFont_command = { 0x1B, 0x4D, 0x00 };// 默认字体
    private byte[] printDouble_command = { 0x1B, 0x47, 0x00 };// 默认不双重打印
    private byte[] line_spacing = { 0x1B, 0x33, 0x00 };// 默认行间距0
    private byte[] word_spacing = { 0x1B, 0x20, 0x00 };// 默认字间距
    /**
     * 34、x坐标 45、y坐标 7、文本长度 8、每行字节数 910、高度
     */
    private byte[] printPicHeader = { 0x1d, 0x3f, 0, 0, 0, 0, 0, 0, 0, 0 };
    private PrinterStatusListener mStatusListener;
    private byte[] cmd;// 打印的数据
    
    private static boolean flag = false;
    private Handler mHandler;
    private static ExecutorService ThreadPool = Executors.newSingleThreadExecutor();
    private Bitmap mBitmap;
    private boolean isover;
    public PrinterAPI() {
        mHandler = new Handler();
    }
    
    public static boolean isFlag() {
        return flag;
    }

    /**
     * 开启模块，上电
     */
    public synchronized void openPrint() {
        SerialPortManager.getInstance().openSerialPortPrinter();
    }

    /**
     * 关闭模块，下电
     */
    public synchronized void closePrint() {
        SerialPortManager.getInstance().closeSerialPort(2);
    }

    /**
     * 函数说明：初始化打印机
     * @param statusListener 打印机状态监听
     */
    public synchronized void initPrint(PrinterStatusListener statusListener) {
        this.mStatusListener=statusListener;
        if(!flag){
            SerialPortManager.getInstance().write(topackage(initPrinter_command));
        }else{
            mStatusListener.work();
        }
        
    }

    /**
     * 函数说明：打印走纸
     * @param statusListener 打印机状态监听
     */
    public synchronized void doPrintPaper(PrinterStatusListener statusListener) {
        this.mStatusListener=statusListener;
        if(!flag){
            SerialPortManager.getInstance().write(topackage(print_command));
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 函数说明：打印一维条码
     * 
     * @param str
     *            打印的一维条码数据
     * @param mBarcodeType
     *            一维条码类型选择
     * @param statusListener 
     *                 打印机状态监听           
     * @return 0：失败 1：成功
     */
    public synchronized void printBarcode(String str, int mBarcodeType,PrinterStatusListener statusListener) {
        this.mStatusListener=statusListener;
        if(!flag){
            try {
                byte[] bytes = str.getBytes("GBK");
                byte[] realBytes = new byte[bytes.length + 4];
                realBytes[0] = 0x1D;
                realBytes[1] = 0x6B;
                realBytes[2] = (byte) mBarcodeType;
                realBytes[3] = (byte) bytes.length;
                byte[] tmpBytes = str.getBytes();
                for (int i = 0; i < bytes.length; i++)
                    realBytes[4 + i] = tmpBytes[i];
                SerialPortManager.getInstance().write(topackage(realBytes));
                doPrintPaper(statusListener);
            } catch (UnsupportedEncodingException e) {
                AppLog.error(TAG, e, "Error Impresora local");
            }
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 函数说明：打印二维码
     * 
     * @param str
     *            二维码数据
     * @param codeType
     *            二维码类型
     * @param statusListener 
     *                 打印机状态监听
     * @return 0：失败 1：成功
     */
    public synchronized int printQrcode(String str, int codeType,PrinterStatusListener statusListener) {
        this.mStatusListener=statusListener;
        if(!flag){
            // 二维码类型
            printQrcode_command[2] = (byte) codeType;
            SerialPortManager.getInstance().write(topackage(printQrcode_command));
    
            // 打印二维码
            try {
                byte[] bytes = str.getBytes("GBK");
                byte[] realBytes = new byte[bytes.length + 4];
                realBytes[0] = 0x1B;
                realBytes[1] = 0x5A;
                realBytes[2] = 0x00;
                realBytes[3] = (byte) bytes.length;
                byte[] tmpBytes = str.getBytes();
                for (int i = 0; i < bytes.length; i++)
                    realBytes[4 + i] = tmpBytes[i];
                SerialPortManager.getInstance().write(topackage(realBytes));
                doPrintPaper(statusListener);
                doPrintPaper(statusListener);
                doPrintPaper(statusListener);
                doPrintPaper(statusListener);
                doPrintPaper(statusListener);
            } catch (UnsupportedEncodingException e) {
                AppLog.error(TAG, e, "Error Impresora local");
                return 0;
            }
            return 1;
        }else{
            mStatusListener.work();
            return 0;
        }
    }

    /**
     * 函数说明：打印Flash中图片
     * 
     * @param imageType
     *            打印Flash图片类型
     * @param statusListener 打印机状态监听
     */
    public synchronized void printFlashImage(final int imageType,PrinterStatusListener statusListener) {
        this.mStatusListener=statusListener;
        if(!flag){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    printFlashPic_command[2] = (byte) imageType;
                    SerialPortManager.getInstance().write(topackage(printFlashPic_command));
                    doPrintPaper(mStatusListener);
                    doPrintPaper(mStatusListener);
                    doPrintPaper(mStatusListener);
                    doPrintPaper(mStatusListener);
                    doPrintPaper(mStatusListener);
                }
            }).start();
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 函数说明： 手动输入打印
     * 
     * @param str
     *            要打印的数据
     */
    public synchronized void printPaper(String str,PrinterStatusListener statusListener) {
        this.mStatusListener=statusListener;
        if(!flag){
            try {
                this.cmd = str.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                AppLog.error(TAG, e, "Error Impresora local");
            }
            mHandler.postDelayed(stopRecycle, 1000 * 30);
            ThreadPool.execute(printer);
//            new Thread(printer).start();
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 设置下划线
     * 
     * @param underline
     *            下划线类型
     * @param statusListener 打印机状态监听
     */
    public synchronized void setUnderLine(int underline,PrinterStatusListener statusListener) {
        this.mStatusListener = statusListener;
        if (!flag) {
            setUnderLine_command[2] = (byte) underline;
            SerialPortManager.getInstance().write(topackage(setUnderLine_command));
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 函数说明：设置对齐方式
     * 
     * @param alignType
     *            对齐类型
     * @param statusListener 打印机状态监听
     */
    public synchronized void setAlighType(int alignType,PrinterStatusListener statusListener) {
        this.mStatusListener = statusListener;
        if (!flag) {
            setAlignType_command[2] = (byte) alignType;
            SerialPortManager.getInstance().write(topackage(setAlignType_command));
        }else{
            mStatusListener.work();
        }
        
    }

    /**
     * 设置宽高粗下划线
     * 
     * @param wide
     *            宽
     * @param high
     *            高
     * @param crude
     *            粗
     * @param underLine
     *            下划线
     * @param statusListener 
     *                  打印机状态监听
     */
    public synchronized void setKGCU(boolean wide, boolean high, boolean crude, boolean underLine,PrinterStatusListener statusListener) {
        this.mStatusListener = statusListener;
        if (!flag) {
            int intWide, intHigh, intCrude, inUnderLine;
            int total;
            if (wide) {
                intWide = 100000;
            } else {
                intWide = 0;
            }
            if (high) {
                intHigh = 10000;
            } else {
                intHigh = 0;
            }
            if (crude) {
                intCrude = 1000;
            } else {
                intCrude = 0;
            }
            if (underLine) {
                inUnderLine = 10000000;
            } else {
                inUnderLine = 0;
            }
            total = intWide + intHigh + intCrude + inUnderLine;
            BigInteger src = new BigInteger(total + "", 2);
            String ten = src.toString();
            int m = Integer.parseInt(ten);
            set_command[2] = (byte) m;
            SerialPortManager.getInstance().write(topackage(set_command));
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 函数说明：设置双重打印
     * 
     * @param tag
     *            是否双重打印
     * @param statusListener 
     *             打印机状态监听           
     */
    public synchronized void setDouble(boolean tag,PrinterStatusListener statusListener) {
        this.mStatusListener = statusListener;
        if (!flag) {
            if (tag) {
                printDouble_command[2] = (byte) 1;
                SerialPortManager.getInstance().write(topackage(printDouble_command));
            } else {
                SerialPortManager.getInstance().write(topackage(printDouble_command));
            }
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 设置行间距
     * 
     * @param space
     *            行间距单位，每单位0.125mm,[0,127]
     * @param statusListener 
     *            打印机状态监听           
     */
    public synchronized void setLineSpace(int space,PrinterStatusListener statusListener) {
        this.mStatusListener = statusListener;
        if (!flag) {
            line_spacing[2] = (byte) space;
            SerialPortManager.getInstance().write(topackage(line_spacing));
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 设置字间距
     * 
     * @param space
     *            字间距单位,space=[0,127]
     * @param statusListener 
     *            打印机状态监听
     */
    public synchronized void setWordSpace(int space,PrinterStatusListener statusListener) {
        this.mStatusListener = statusListener;
        if (!flag) {
            word_spacing[2] = (byte) space;
            SerialPortManager.getInstance().write(topackage(word_spacing));
        }else{
            mStatusListener.work();
        }
    }

    /**
     * 函数说明：设置字体
     * 
     * @param tag
     *            字体类型
     * @param statusListener 
     *            打印机状态监听           
     */
    public synchronized void setFont(int fontType,PrinterStatusListener statusListener) {
        this.mStatusListener = statusListener;
        if (!flag) {
            printFont_command[2] = (byte) fontType;
            SerialPortManager.getInstance().write(topackage(printFont_command));
        }else{
            mStatusListener.work();
        }
    }

    /**
     * Print picture,the height of picture can not be too long.
     * 
     * @param bitmap
     * @param printerPicListener
     * @param statusListener
     */
    public synchronized void printPic(Bitmap bitmap, PrinterStatusListener statusListener) {
        try{
            this.mStatusListener = statusListener;
            Log.d("jokey", "enter printPic  "+flag);
            if (!flag) {
                this.mBitmap = bitmap;
                mHandler.postDelayed(stopRecycle, 1000 * 30);
                ThreadPool.execute(printerPic);
//                new Thread(printerPic).start();
            }else{
                mStatusListener.work();
            }
        }catch(Exception e){
            AppLog.error(TAG, e, "Error Impresora local");
        }
    }

    /**
     * 打印view
     * 
     * @param v
     *            要打印的view
     * @param statusListener
     *            状态监听
     */
    public synchronized void printView(View v, PrinterStatusListener statusListener) {
        try{
            this.mStatusListener = statusListener;
            Log.d("jokey", "printView1");
            if(!flag){
                printPic(Bmp.getBitmapFromView(v), statusListener);
            }else
                mStatusListener.work();
        }catch(Exception e){
            AppLog.error(TAG, e, "Error Impresora local");
        }
    }

    /**
     * 打印，当指令长度大于250请使用此方法打印
     * 
     * @param cmd
     *            下发的指令
     */
    private void print(byte[] cmd) throws Exception{
        int length = cmd.length;
        byte[] data;// 分组下发的指令
        int t = 1000;
        if (length > t) {// 如果大于1000个字节
            int n = length / t + 1;// 分几组发下去
            Log.w("zzd", "baoshu:  "+n);
            for (int i = 0; i < n; i++) {
                if(flag){
                    if (i != n - 1) {// 如果不是最后一组
                        data = new byte[t];
                        System.arraycopy(cmd, t * i, data, 0, t);
                        if(flag)
                            SerialPortManager.getInstance().write(topackage(data));
                        data = null;
                    } else {
                        data = new byte[length % t];
                        System.arraycopy(cmd, t * i, data, 0, data.length);
                        if(flag)
                            SerialPortManager.getInstance().write(topackage(data));
                        data = null;
                    }
                }else{
                    break;
                }
            }
        } else {
            if(flag)
                SerialPortManager.getInstance().write(topackage(cmd));
        }
    }

    /**
     * 封装指令并返回
     * 
     * @param old
     *            原始指令
     * @return 新的指令
     */
    private byte[] topackage(byte[] old) {
        int len = old.length;
        byte[] bytes = int2byte(len);
        byte[] heade = { (byte) 0xCA, (byte) 0xDF, (byte) 0x00, (byte) 0x35 };
        byte[] cmd = new byte[heade.length + 2 + len + 1];
        System.arraycopy(heade, 0, cmd, 0, heade.length);
        cmd[heade.length] = bytes[1];
        cmd[heade.length + 1] = bytes[0];
        System.arraycopy(old, 0, cmd, heade.length + 2, len);
        cmd[cmd.length - 1] = (byte) 0xE3;
        return cmd;
    }

    private byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);
        targets[1] = (byte) ((res >> 8) & 0xff);
        targets[2] = (byte) ((res >> 16) & 0xff);
        targets[3] = (byte) (res >>> 24);
        return targets;
    }

    public synchronized int printQrcode1(String str, int codeType,  PrinterStatusListener statusListener ) {
        // ����ά������
        this.mStatusListener = statusListener;
        printQrcode_command[2] = (byte) codeType;
        SerialPortManager.getInstance().write(topackage(printQrcode_command));

        // ��ӡ��ά��
        try {
            byte[] bytes = str.getBytes("GBK");
            byte[] realBytes = new byte[bytes.length + 4];
            realBytes[0] = 0x1B;
            realBytes[1] = 0x5A;
            realBytes[2] = 0x00;
            realBytes[3] = (byte) bytes.length;
            byte[] tmpBytes = DataUtils.hexStringTobyte(DataUtils
                    .str2Hexstr(str));
            for (int i = 0; i < bytes.length; i++)
                realBytes[4 + i] = tmpBytes[i];
            SerialPortManager.getInstance().write(topackage(realBytes));
            doPrintPaper(statusListener);
            doPrintPaper(statusListener);
            doPrintPaper(statusListener);
            doPrintPaper(statusListener);
            doPrintPaper(statusListener);
        } catch (UnsupportedEncodingException e) {
            AppLog.error(TAG, e, "Error Impresora local");
            return 0;
        }
        return 1;
    }

    public interface PrinterStatusListener {
        void work();
        
        void hot();

        void noPaper();

        void end();
    }

    /**
     * 打印文字
     */
    private Runnable printer = new Runnable() {

        @Override
        public void run() {
            try {
                flag = true;
                Log.d("jokey", "Runnable printer  "+flag);
                print(cmd);
                isreceiveEnd(true);
            } catch (Exception e) {
                AppLog.error(TAG, e, "Error Impresora local");
            }
        }
    };

    private Runnable printerPic = new Runnable() {

        @Override
        public void run() {
            try {
                SerialPortManager.getInstance().clear();
                flag = true;
                Log.d("jokey", "Runnable printerPic  "+flag);
                Bmp bmp = null;
                if(mBitmap!=null){
                    bmp = new Bmp(mBitmap);
                    if(bmp!=null){
                        int width = bmp.getW();
                        int height = bmp.getHeight();
                        byte[] data = bmp.getRealImageData();
            
                        sendPicData(width, height, data);
                        Log.d("zzd", "out");
                    }
                    mBitmap = null;
                }
            } catch (Exception e) {
                AppLog.error(TAG, e, "Error Impresora local");
            }
        }
    };

    private Runnable stopRecycle = new Runnable() {
        @Override
        public void run() {
            flag = false;
            Log.d("jokey", "Runnable stopRecycle  "+flag);
        }
    };

    private void sendPicData(int width, int height, byte[] imageData) throws Exception{
        int maxH = 300;// 最大高度
        int n = height / maxH + 1;// 分几段下发图片数据
        byte[] data = null;// 每段图片数据
        int h = 0;// 每段图片的高度
        boolean isLast = false;// 是否是最后一段
        for (int i = 0; i < n; i++) {
            if(flag){
                Log.d("zzd", "第：" + i);
                h = i == n - 1 ? height - (i * maxH) : maxH;// 判断是否是最后一段
                isLast = i == n - 1 ? true : false;
                data = new byte[width * h];
                System.arraycopy(imageData, width * maxH * i, data, 0, data.length);
                Log.d("zzd", "send:" + i);
                print(picCMD(width, h, data));
                isreceiveEnd(isLast);
            }else{
                break;
            }
        }
        mHandler.removeCallbacks(stopRecycle);
    }
    
    public void close(){
        Log.d("jokey", "close  "+flag);
        flag=false;
        isover = true;
//        mBitmap = null;
//        System.gc();
    }

    /**
     * 打印图片指令
     * 
     * @param width
     *            图片宽度
     * @param height
     *            图片高度
     * @param data
     *            图片数据
     * @return 指令
     */
    private byte[] picCMD(int width, int height, byte[] data) {
        printPicHeader[printPicHeader.length - 3] = (byte) width;
        printPicHeader[printPicHeader.length - 2] = (byte) ((height & 0xff00) >> 8);
        printPicHeader[printPicHeader.length - 1] = (byte) (height & 0x00ff);
        byte[] cmd = new byte[printPicHeader.length + data.length];
        System.arraycopy(printPicHeader, 0, cmd, 0, printPicHeader.length);
        System.arraycopy(data, 0, cmd, printPicHeader.length, data.length);
        return cmd;
    }

    private void isreceiveEnd(boolean islast) {
        isover = false;// 是否收到结束符
        SerialPortManager.getInstance().clear();
        while (!isover) {
            if (SerialPortManager.getInstance().getIen() == 2) {
                byte[] state = SerialPortManager.getInstance().getBuffer();
//                Log.d("zzd", "state: "+state[0]+" : "+state[1]);
                if (state[0] == 2) {
                    switch (state[1]) {
                    case 1:
                        mHandler.post(new Runnable() {
                            public void run() {
                                mStatusListener.noPaper();
                            }
                        });
                        mHandler.removeCallbacks(stopRecycle);
                        isover = true;
                        Log.d("zzd", "no paper");
                        break;
                    case 4:
                        mHandler.post(new Runnable() {
                            public void run() {
                                mStatusListener.hot();
                            }
                        });
                        mHandler.removeCallbacks(stopRecycle);
                        isover = true;
                        Log.d("zzd", "hot");
                        break;
                    case 8:
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            AppLog.error(TAG, e, "Error Impresora local");
                        }
                        isover = true;
                        Log.d("zzd", "stop");
                        break;
                    }
                }
            }else{
                isover = true;
                Log.d("jokey", "isover!");
            }
        }
        if (islast){
            flag = false;
            Log.d("jokey", "isreceiveEnd--islast  "+flag);
            mHandler.post(new Runnable() {
                public void run() {
                    mStatusListener.end();
                }
            });
        }
    }

    /**
     * 创建二维码图像
     * 
     * @param contents
     *            内容
     * @param type
     *            类型
     * @param widthAndHeight
     *            宽高
     * @return
     * @throws WriterException
     */
    public Bitmap createQRCode(String contents, BarcodeFormat type, int widthAndHeight, String encode) throws WriterException {
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, encode);
        BitMatrix matrix = new MultiFormatWriter().encode(contents, type, widthAndHeight, widthAndHeight,hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 创建条码
     *
     * @param context
     * @param contents
     *            内容
     * @param type
     *            类型
     * @param desiredWidth
     *            宽度
     * @param desiredHeight
     *            高度
     * @param displayCode
     *            是够显示条码信息
     * @return
     * @throws WriterException
     */
    public Bitmap createBarCode(Context context, String contents, BarcodeFormat type, int desiredWidth,
                                int desiredHeight, boolean displayCode) throws WriterException {
        Bitmap ruseltBitmap = null;
        BarcodeFormat barcodeFormat = type;
        if (displayCode) {
            Bitmap barcodeBitmap = encodeAsBitmap(contents, barcodeFormat, desiredWidth, desiredHeight);
            Bitmap codeBitmap = creatCodeBitmap(contents, desiredWidth, 30, context);
            ruseltBitmap = mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(0, desiredHeight));
        } else {
            ruseltBitmap = encodeAsBitmap(contents, barcodeFormat, desiredWidth, desiredHeight);
        }
        return ruseltBitmap;
    }

    private Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int desiredWidth, int desiredHeight)
            throws WriterException {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, format, desiredWidth,
                    desiredHeight, null);
        } catch (WriterException e) {
            AppLog.error(TAG, e, "Error Impresora local");
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private Bitmap creatCodeBitmap(String contents, int width, int height, Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                width, LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setText(contents);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setWidth(width);
        tv.setDrawingCacheEnabled(true);
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundColor(Color.WHITE);
        tv.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

        tv.buildDrawingCache();
        Bitmap bitmapCode = tv.getDrawingCache();
        return bitmapCode;
    }

    private Bitmap mixtureBitmap(Bitmap first, Bitmap second, PointF fromPoint) {
        if (first == null || second == null || fromPoint == null) {
            return null;
        }
        Bitmap newBitmap = Bitmap.createBitmap(
                first.getWidth(),
                first.getHeight() + second.getHeight(), Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, 0, 0, null);
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save();
        cv.restore();

        return newBitmap;
    }
}
