package com.ziruk.oa.communitymodule.ui.StoriedBuilding;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.base.activity.BaseFragmentActivity;


public class StoriedBuildingList_ActivityDemo extends BaseFragmentActivity {

    private static DrawerLayout mDrawerLayout;

    private StoriedBuildingList_FilterDemo filterFragment = null;
    private StoriedBuildingList_ContentDemo contentFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initListener();

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_gzsb_guzhang_shangbao_list;
    }

    @Override
    public void setHeader() {
        setToolBarTitle("财产列表");
    }

    private void initView() {

        mDrawerLayout = (DrawerLayout ) findViewById(R.id.drawer_layout);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        filterFragment = new StoriedBuildingList_FilterDemo();
        transaction.replace(R.id.left_drawer, filterFragment);

        contentFragment = new StoriedBuildingList_ContentDemo();
        transaction.replace(R.id.content_frame, contentFragment);

        transaction.commit();

    }

    private void initListener() {

        showToolbarFilter("筛选", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)){

                    mDrawerLayout.closeDrawer(Gravity.RIGHT);

                }else {

                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }

            }
        });
    }

    public static void CloseDrawer(){

        mDrawerLayout.closeDrawer(Gravity.RIGHT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
