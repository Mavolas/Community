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


    public static String getURLRoot(Context context)
    {

        String testServerWifiName = GetAndroidString(
                context,
                UrlConstant.AndroidConstantID_TestServerWifiName);

        String URLRoot="";

        if ( testServerWifiName.equals( GetWifiName( context ) ) ){

            URLRoot= GetAndroidString(
                    context,
                    UrlConstant.AndroidConstantID_TestURLRoot);
        }else {

            URLRoot= GetAndroidString(
                    context,
                    UrlConstant.AndroidConstantID_URLRoot);
        }

        if (!URLRoot.endsWith("/")){
            URLRoot += "/";
        }
        return URLRoot;

    }


    public static String GetWifiName(Context context){

        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);

        WifiInfo wifiInfo = null;
        if ( wifiManager != null ) {
            wifiInfo = wifiManager.getConnectionInfo();
        }
//        Log.d("wifiInfo", wifiInfo.toString());
//        Log.d("SSID",wifiInfo.getSSID());

        return wifiInfo.getSSID();
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
