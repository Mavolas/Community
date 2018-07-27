package com.ziruk.oa.communitymodule.application;


import android.app.Activity;
import android.app.Application;
import android.content.Context;



import com.ziruk.oa.communitymodule.capabilities.zirukhttp.ZirukHttpClient;
import com.ziruk.oa.communitymodule.util.Config;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.finalteam.rxgalleryfinal.imageloader.FrescoImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.GlideImageLoader;
import okhttp3.OkHttpClient;


public class FMApplication extends Application {

    protected static FMApplication instance;

    private static Context sContext;

    private List<Activity> mList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();

        instance =this;
        sContext = getApplicationContext();


        //配置网络框架的参数
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .build();
        ZirukHttpClient.initClient(okHttpClient);


//        BigImageViewer.initialize(FrescoImageLoader.setImageSmall(  ););


//        String address = Config.LoadConfig(getApplicationContext(), "ServerAddress");
//        if (StringUtils.isBlank(address)) {
//            address = ConstantUtils.GetAndroidString(
//                    sContext,
//                    UrlConstant.AndroidConstantID_URLRoot);
//            Config.SaveConfig(sContext, "ServerAddress", address);
//        }


//        CrashHandler crashHandler = CrashHandler.getInstance();
//        // 注册crashHandler
//        crashHandler.init(getApplicationContext());
//        // 发送以前没发送的报告(可选)
//        crashHandler.sendPreviousReportsToServer();

    }

    public static FMApplication getInstance() {
        return instance;
    }


    public void addActivity(Activity activity) {
        mList.add(activity);
    }


    public static Context getAppContext() {
        return sContext;
    }


}
