package com.ziruk.oa.communitymodule.util;

import android.content.Context;

import com.ziruk.oa.communitymodule.application.FMApplication;


/**
 * Created by 宋棋安
 * on 2018/6/21.
 */
public class ScreenUtils {


    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dip2px(float dpValue) {
        Context context = FMApplication.getInstance();
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
