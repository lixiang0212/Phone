package com.androidy.azsecuer.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Display;
import android.widget.Toast;

import com.androidy.azsecuer.util.PublicUtils;
import com.androidy.azsecuer.util.TestChildInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PhoneTestManager {

    private Context context;
    private WifiManager wifiManager;
    private ConnectivityManager connManager;
    private PackageManager packageManager;

    public PhoneTestManager(Context context) {
        this.context = context;
        wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        connManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        packageManager =context.getPackageManager();
    }
    // 是否同意指定权限(Manifest.permission.ACCESS_NETWORK_STATE)
    public boolean isGranterPermission(String permission) {
        int result = packageManager.checkPermission(permission, context.getPackageName());
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    //获取内存信息
    public ArrayList<TestChildInfo> getMemoryMessage() {
        ArrayList<TestChildInfo> childs = new ArrayList<TestChildInfo>();
        String maxRam =PublicUtils.formatSize(MemoryManager.getTotalRamMemory());
        String freeRam = PublicUtils.formatSize(MemoryManager.getAvailRamMemory(context));
        String inRom = PublicUtils.formatSize(MemoryManager.getTotalInRom(context));
        String outRom = PublicUtils.formatSize(MemoryManager.getTotalOutRom(context));
        File file_in = Environment.getDataDirectory();
        StatFs sf_in = new StatFs(file_in.getPath());
        //内置空间
        long total_in =sf_in.getTotalBytes();
        long avai_in =sf_in.getAvailableBytes();
        //外置空间
        File file_out =Environment.getExternalStorageDirectory();
        StatFs sf_out = new StatFs(file_out.getPath());
        long total_out = sf_out.getTotalBytes();
        long avai_out = sf_out.getAvailableBytes();
        childs.add(new TestChildInfo("最大RAM:", maxRam));
        childs.add(new TestChildInfo("空闲RAM:", freeRam));
        childs.add(new TestChildInfo("内置空间:", PublicUtils.formatSize(total_out)));
        childs.add(new TestChildInfo("外置空间:", PublicUtils.formatSize(total_in)));
        return childs;
    }
    // 设备信息 -------------------------------------------------
    public ArrayList<TestChildInfo> getPhoneMessage() {
        ArrayList<TestChildInfo> childs = new ArrayList<TestChildInfo>();

        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        String name = metrics.widthPixels + "*" + metrics.heightPixels;

        childs.add(new TestChildInfo("手机品牌:", Build.BRAND.toUpperCase()));
        childs.add(new TestChildInfo("手机制造商:", Build.MANUFACTURER));
        childs.add(new TestChildInfo("手机型号:", Build.MODEL.toUpperCase()));
        childs.add(new TestChildInfo("手机分辨率:", name));
        return childs;
    }

    // 系统信息 -------------------------------------------------
    public ArrayList<TestChildInfo> getSystemMessage() {
        ArrayList<TestChildInfo> childs = new ArrayList<TestChildInfo>();
        childs.add(new TestChildInfo("系统版本:", Build.VERSION.RELEASE));
        childs.add(new TestChildInfo("基带版本:", Build.VERSION.CODENAME));
        return childs;
    }
    // 网络信息 -------------------------------------------------
    public ArrayList<TestChildInfo> getWIFIMessage(Context context) {
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
//            Toast.makeText(context,"GG",Toast.LENGTH_SHORT).show();
        }
        WifiInfo info = wifiManager.getConnectionInfo();
        ArrayList<TestChildInfo> childs = new ArrayList<>();
        childs.add(new TestChildInfo("网络类型:", networkInfo.getTypeName()));
        childs.add(new TestChildInfo("WIFI名称:", info.getSSID() + ""));
        childs.add(new TestChildInfo("WIFI-IP:", longToIP(info.getIpAddress())+""));
        childs.add(new TestChildInfo("WIFI速度:", info.getLinkSpeed() + ""));
        return childs;
    }
    private String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        sb.append(".");
        // 将高1位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        return sb.toString();
    }
    // 相机信息 -------------------------------------------------
    public ArrayList<TestChildInfo> getCameraMessage() {
        ArrayList<TestChildInfo> childs = new ArrayList<TestChildInfo>();
        childs.add(new TestChildInfo("相机像素:", getCameraResolution()));
        childs.add(new TestChildInfo("闪光灯状态:", getCameraFlashMode()));
        return childs;
    }
    /** 获取相机像素 */
    public String getCameraResolution() {
        String cameraResolution = "";
        try {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            Camera.Size size = null;
            for (Camera.Size s : sizes) {
                if (size == null) {
                    size = s;
                } else if (size.height * s.width < s.height * s.width) {
                    size = s;
                }
            }
            cameraResolution = (size.width * size.height) / 10000 + "万像素";
            camera.release();
        } catch (Exception e) {
            return "访问出错";
        }
        return cameraResolution;
    }
    /** 获取闪光灯状态 */
    public String getCameraFlashMode() {
        String flashMode = "";
        try {
             Camera camera = Camera.open();
             Camera.Parameters parameters = camera.getParameters();
             flashMode = parameters.getFlashMode();
             if (flashMode.equals(Camera.Parameters.FLASH_MODE_AUTO)) {
             flashMode = "自动模式";
             }
             if (flashMode.equals(Camera.Parameters.FLASH_MODE_OFF)) {
             flashMode = "关闭模式";
             }
             if (flashMode.equals(Camera.Parameters.FLASH_MODE_ON)) {
             flashMode = "打开模式";
             }
             camera.release();
             flashMode = "其它模式";
        } catch (Exception e) {
            flashMode = "访问出错";
            return flashMode;
        }
        return flashMode;
    }


}
