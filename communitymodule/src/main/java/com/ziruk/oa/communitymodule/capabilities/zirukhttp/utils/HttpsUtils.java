package com.ziruk.oa.communitymodule.capabilities.zirukhttp.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.ziruk.oa.communitymodule.R;


import java.lang.reflect.Field;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by 宋棋安
 * on 2018/7/4.
 */
public class HttpsUtils {

    final public static String AndroidConstantID_URLRoot = "cst_url_root";


//    public void GetWifiName(Context context){
//
//        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//
//
//        Log.d("wifiInfo", wifiInfo.toString());
//        Log.d("SSID",wifiInfo.getSSID());
//
//    }


    public static String getURLRoot(Context context)
    {
       String URLRoot = GetAndroidString(
               context,
               AndroidConstantID_URLRoot);

        if (!URLRoot.endsWith("/")){
            URLRoot += "/";
        }
        return URLRoot;

    }


    public static String GetAndroidString(Context context, String key)
    {
        try {
            Object RObj = new R.string();
            Field field = RObj.getClass().getField(key);
            int index = field.getInt(RObj);

            String value = context.getResources().getString(index);

            return value;
        } catch (Exception e) {
            return "";
        }
    }


}
