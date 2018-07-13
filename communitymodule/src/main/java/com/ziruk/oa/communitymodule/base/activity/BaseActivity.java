package com.ziruk.oa.communitymodule.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public abstract class BaseActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_AUTO_LOGIN = 901;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //取消标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //初始化页面
        setContentView(getContentViewId());

        initViews();

        initData();

        initListener();

    }

    public abstract int getContentViewId();

    public abstract void initViews();

    public abstract void initData();

    public abstract void initListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
