package com.ziruk.oa.communitymodule.ui.StoriedBuilding;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.base.activity.ToolBarActivity;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.SpinnerOption;
import com.ziruk.oa.communitymodule.uiCommon.activity.MainActivity;
import com.ziruk.oa.communitymodule.util.ToastUtil;
import com.ziruk.oa.communitymodule.view.spinner.MySpinner;
import com.ziruk.oa.communitymodule.view.spinner.ValueTextCls;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.activity.MediaActivity;
import cn.finalteam.rxgalleryfinal.utils.Logger;
import cn.finalteam.rxgalleryfinal.utils.PermissionCheckUtils;

/**
 * Created by 宋棋安
 * on 2018/6/22.
 */
public class StoriedBuildingDetail_ActivityDemo extends ToolBarActivity {


    SimpleDraweeView simpleDraweeView;
    @Override
    public void setHeader() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_building_detail;
    }

    @Override
    public void initViews() {

        final MySpinner mspinner = (MySpinner) findViewById(R.id.msp_test);

        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);

        Button button = (Button) findViewById(R.id.btn_uploads);


        simpleDraweeView.setImageURI("http://pic27.photophoto.cn/20130515/0037037553414509_b.jpg" );




//        List<ValueTextCls> list = new ArrayList<ValueTextCls>();
//        ValueTextCls spinnerItem=new ValueTextCls("1", "测试数据1");
//        ValueTextCls spinnerItem2=new ValueTextCls("2", "测试数据2");
//        list.add(spinnerItem);
//        list.add(spinnerItem2);




        mspinner.setDataBindListener( new MySpinner.OnDataBindingBeginListener( ) {
            @Override
            public List <ValueTextCls> getData() {


                List<ValueTextCls> list = new ArrayList<ValueTextCls>();

                list.add(new ValueTextCls("我是0","全部"));

                list.add(new ValueTextCls("我是1","1"));
                list.add(new ValueTextCls("我是2","2"));
                list.add(new ValueTextCls("我是3","3"));
                list.add(new ValueTextCls("我是4","4"));


                return list;
            }
        } );


        mspinner.fresh();



        button.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                openImageSelect();
            }
        } );



    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


    private void openImageSelect() {

        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(StoriedBuildingDetail_ActivityDemo.this);

        //设置自定义的参数
        instance
                .setType(RxGalleryFinalApi.SelectRXType.TYPE_IMAGE, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_RADIO)
                .setImageRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        ToastUtil.showToastShort( "图片选择" +imageRadioResultEvent.getResult().getThumbnailBigPath());

                        simpleDraweeView.setImageURI("file://" +imageRadioResultEvent.getResult().getThumbnailSmallPath() );
                    }
                }).open();


    }


}
