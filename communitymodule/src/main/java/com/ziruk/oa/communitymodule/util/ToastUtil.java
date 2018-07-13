package com.ziruk.oa.communitymodule.util;

import android.widget.Toast;

import com.ziruk.oa.communitymodule.application.FMApplication;


public class ToastUtil {

    private static Toast toast;
    /**
     * 自定义Toast
     *
     * @param message
     */
    public static void showToastShort(CharSequence message) {
        if (toast == null) {
            toast = Toast.makeText( FMApplication.getInstance(),
                    message,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

      public static void  showToastLong(CharSequence message) {
        if (toast == null) {
            toast = Toast.makeText(FMApplication.getInstance(),
                    message,
                    Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
