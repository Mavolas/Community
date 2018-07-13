package com.ziruk.oa.communitymodule.base.activity;

/**
 * Created by 宋棋安
 * on 2018/6/15.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.application.FMApplication;
import com.ziruk.oa.communitymodule.util.ScreenUtils;


public abstract class BaseFragmentActivity extends FragmentActivity {

    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    private TextView mToolbarSub2Title;
    private Button mToolbarFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentViewId());
        FMApplication.getInstance().addActivity(this);


        mToolbar = (Toolbar ) findViewById( R.id.toolbar);

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
        mToolbarSub2Title = (TextView) findViewById(R.id.toolbar_sub2title);
        mToolbarSub2Title = (TextView) findViewById(R.id.toolbar_sub2title);
        mToolbarFilter = (Button) findViewById(R.id.toolbar_filter);


        //隐藏副标题和筛选
        getSubTitle().setVisibility(View.GONE);
        getSub2Title().setVisibility(View.GONE);

        //子类实现，设置头部相关
        setHeader();
    }

    public abstract int getContentViewId();

    public abstract void setHeader();

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if (null != getToolbar() && isShowBacking()) {
            showBack();
        }

    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getSubTitle() {
        return mToolbarSubTitle;
    }

    /**
     * 获取副头部标题的TextView
     *
     * @return
     */
    public TextView getSub2Title() {
        return mToolbarSub2Title;
    }

    /**
     * 获取头部Filter
     *
     * @return
     */
    public Button getToolbarFilter() {
        return mToolbarFilter;
    }


    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {

        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    /**
     * this Activity of tool bar.
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar ) findViewById(R.id.toolbar);
    }

    /**
     * 显示后退按钮
     */
    private void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.mipmap.back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    public boolean isShowBacking() {
        return true;
    }

    /**
     * 设置筛选按钮
     * @param text 有文字时显示文字，无文字时显示默认图片
     * @param btnFilterClick
     */
    public void showToolbarFilter(String text, View.OnClickListener btnFilterClick) {

        getToolbarFilter().setVisibility(View.VISIBLE);

        if (text != null && text!="" ) {
            getToolbarFilter().setText(text);
        } else {
            getToolbarFilter().setBackgroundResource(R.mipmap.filter);
            ViewGroup.LayoutParams linearParams = getToolbarFilter().getLayoutParams();
            linearParams.height = ScreenUtils.dp2px(this, 22);
            linearParams.width = ScreenUtils.dp2px(this, 22);
            getToolbarFilter().setLayoutParams(linearParams);

            getToolbarFilter().setText(text);
        }

        getToolbarFilter().setOnClickListener(btnFilterClick);
    }



}
