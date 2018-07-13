package com.ziruk.oa.communitymodule.uiCommon.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.j256.ormlite.misc.VersionUtils;
import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.capabilities.boot.BootLoader;


import org.apache.commons.lang.StringUtils;

/**
 * Created by 宋棋安
 * on 2018/6/15.
 */
public class SplashActivity extends AppCompatActivity {

    private TextView LinkURL;
    private TextView Version;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.activity_splash);

        //设置沉浸式状态栏
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();

        //设置布局
        initView();
        //检查更新
//        doUpdate();


         BootLoader loader = new BootLoader();
         loader.Load(SplashActivity.this, true);
         SplashActivity.this.finish();
    }

    private void initView() {

        Version = (TextView) findViewById(R.id.tv_version);
        LinkURL = (TextView) findViewById(R.id.tv_url);

//        String url = HttpBaseCls.GetURLRoot(this);
//        url = StringUtils.replace(url, "http://", "");
//        url = StringUtils.left(url, url.length()-1);
//
//        Version.setText( VersionUtils.getVersionTxt(this));
//        LinkURL.setText(url);

    }

//    private void doUpdate() {
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //检查更新，跳转页面
//                VersionUtils.CheckNewVersion(SplashActivity.this,
//                        new VersionCheckNoNewListener() {
//                            @Override
//                            public void doWhenNoUpdate() {
//
//                                BootLoader loader = new BootLoader();
//                                loader.Load(SplashActivity.this, true);
//                                SplashActivity.this.finish();
//
//                            }
//                        });
//
//
//            }
//        }, 3000);
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
