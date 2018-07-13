package com.ziruk.oa.communitymodule.uiCommon.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.base.fragment.BaseFragment;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.StoriedBuildingList_ActivityDemo;
import com.ziruk.oa.communitymodule.uiCommon.fragment.bean.HomeSend;
import com.ziruk.oa.communitymodule.util.CurrentUserInfoUtils;


import com.ziruk.oa.communitymodule.view.NoScrollGridView;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends BaseFragment {


    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setHeader() {
        setToolBarTitle("主页");
    }


    @Override
    public void initView(View view) {

        TextView mToolbarTitle = ( TextView ) view.findViewById( R.id.toolbar_title );
        TextView mToolbarSubtitle = ( TextView ) view.findViewById( R.id.toolbar_subtitle );
        Button mToolbarFilter = ( Button ) view.findViewById( R.id.toolbar_filter );
        TextView mToolbarSub2title = ( TextView ) view.findViewById( R.id.toolbar_sub2title );
        Toolbar mToolbar = ( Toolbar ) view.findViewById( R.id.toolbar );
        LinearLayout mLlGroup2 = ( LinearLayout ) view.findViewById( R.id.llGroup2 );
        NoScrollGridView mGridView2 = ( NoScrollGridView ) view.findViewById( R.id.gridView2 );
        LinearLayout mLlGroup3 = ( LinearLayout ) view.findViewById( R.id.llGroup3 );
        NoScrollGridView mGridView3 = ( NoScrollGridView ) view.findViewById( R.id.gridView3 );
        LinearLayout mLlGroup1 = ( LinearLayout ) view.findViewById( R.id.llGroup1 );
        NoScrollGridView mGridView1 = ( NoScrollGridView ) view.findViewById( R.id.gridView1 );


        drawMenuGrid2(mGridView2, mLlGroup2);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    private void drawMenuGrid2(GridView gridview, LinearLayout ll) {
        ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();

        int cntMenu = 0;
        //[start] 菜单项目添加
        if ( CurrentUserInfoUtils.hasPower(getContext(), "01")) {
            map = new HashMap<String, Object>();
            map.put("ItemImage", R.mipmap.ic_menu_01);
            map.put("ItemText", "采购查询");
            meumList.add(map);
            cntMenu++;
        }

        if (CurrentUserInfoUtils.hasPower(getContext(), "02")) {
            map = new HashMap<String, Object>();
            map.put("ItemImage", R.mipmap.ic_menu_01);
            map.put("ItemText", "评标管理");
            meumList.add(map);
            cntMenu++;
        }

        if (CurrentUserInfoUtils.hasPower(getContext(), "03")) {
            map = new HashMap<String, Object>();
            map.put("ItemImage", R.mipmap.ic_menu_01);
            map.put("ItemText", "审批管理");
            meumList.add(map);
            cntMenu++;
        }

        if (CurrentUserInfoUtils.hasPower(getContext(), "04")) {
            map = new HashMap<String, Object>();
            map.put("ItemImage", R.mipmap.ic_menu_01);
            map.put("ItemText", "废标审批");
            meumList.add(map);
            cntMenu++;
        }


        if (cntMenu == 0) {
            gridview.setVisibility( View.GONE);
            ll.setVisibility( View.GONE);
        } else {
            gridview.setVisibility( View.VISIBLE);
            ll.setVisibility( View.VISIBLE);
        }

        if (cntMenu % 4 != 0) {
            int cntBlank = 4 - cntMenu % 4;

            for (int k = 0; k < cntBlank; k++) {
                map = new HashMap<String, Object>();
                map.put("ItemImage", R.mipmap.ic_menu_blank);
                map.put("ItemText", "");
                meumList.add(map);
            }
        }
        //[end]

        SimpleAdapter saItem = new SimpleAdapter(getContext(), meumList,
                R.layout.activity_menu_item, new String[]{"ItemImage",
                "ItemText"},
                new int[]{R.id.ItemImage, R.id.itemText});

        // 添加Item到网格中
        gridview.setAdapter(saItem);

        // 添加点击事件
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int index = arg2 + 1;// id是从0开始的，所以需要+1
                Intent intent = new Intent();

                int i = 1;

                if (CurrentUserInfoUtils.hasPower(getContext(), "01")) {
                    // 调度执行
                    if (index == i++) {

                        //发送数据
                        EventBus.getDefault().postSticky(new HomeSend("01"));

                        //启动activity
                        intent.setClass(getContext(), StoriedBuildingList_ActivityDemo.class);
                        getContext().startActivity(intent);

                    }

                }
                if (CurrentUserInfoUtils.hasPower(getContext(), "02")) {
                    // 位置绑定
                    if (index == i++) {
                        EventBus.getDefault().post(new HomeSend("01"));
                    }

                }
                if (CurrentUserInfoUtils.hasPower(getContext(), "03")) {
                    // 设备维修
                    if (index == i++) {
//                        intent.setClass(getContext(), arcgisMap.class);
                        intent.putExtra("mode", "1");
                        getContext().startActivity(intent);
                    }

                }
                if (CurrentUserInfoUtils.hasPower(getContext(), "04")) {
                    // 设备保养
                    if (index == i++) {
//                        intent.setClass(getContext(), StoriedBuildingList_ActivityDemo.class);
                        intent.putExtra("mode", "2");
                        getContext().startActivity(intent);
                    }

                }


            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }
}
