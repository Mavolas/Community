package com.ziruk.oa.communitymodule.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ziruk.oa.communitymodule.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 宋棋安
 * on 2018/6/15.
 */
public abstract class BaseFragment extends Fragment {


    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    private TextView mToolbarSub2Title;

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(getContentViewId(),container,false);

        unbinder = ButterKnife.bind(this, view);

        mToolbar = (Toolbar) view.findViewById( R.id.toolbar);

        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) view.findViewById(R.id.toolbar_subtitle);
        mToolbarSub2Title = (TextView) view.findViewById(R.id.toolbar_sub2title);
        mToolbarSub2Title = (TextView) view.findViewById(R.id.toolbar_sub2title);


        //隐藏副标题和筛选
        getSubTitle().setVisibility( View.GONE);
        getSub2Title().setVisibility( View.GONE);


        //子类实现，设置头部相关
        setHeader();

        initView(view);

        initData();

        initEvent();

        return view;

    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        getMenuInflater().inflate(R.<span style="color:#ff0000;">menu.toolbar_menu</span>, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_scan:
//                ShowUtils.showToast("点击了扫描");
//                break;
//            case R.id.action_add:
//                ShowUtils.showToast("点击了添加");
//                break;
//            case R.id.action_item1:
//                ShowUtils.showToast("点击了菜单1");
//                break;
//            case R.id.action_item2:
//                ShowUtils.showToast("点击了菜单2");
//                break;
//            default:break;
//        }
//        return true;
//
//    }



    public abstract int getContentViewId();

    protected abstract void setHeader();

    public abstract void initView(View view);

    protected abstract void initData();

    protected abstract void initEvent();


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
     *
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
