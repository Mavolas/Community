package com.ziruk.oa.communitymodule.uiCommon.fragment.bean;

import java.io.Serializable;

/**
 * Created by 宋棋安
 * on 2018/6/22.
 */
public class HomeSend implements Serializable {

    public String ScreenType;

    public HomeSend(String screenType) {
        ScreenType = screenType;
    }
}
