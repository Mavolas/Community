package com.ziruk.oa.communitymodule.ui.StoriedBuilding;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.base.activity.ToolBarActivity;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.Attachement;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.GalleryFileInfo;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.GalleryFileType;
import com.ziruk.oa.communitymodule.util.BitmapUtils;
import com.ziruk.oa.communitymodule.util.ToastUtil;
import com.ziruk.oa.communitymodule.view.NoScrollGridView;
import com.ziruk.oa.communitymodule.view.spinner.MySpinner;
import com.ziruk.oa.communitymodule.view.spinner.ValueTextCls;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * Created by 宋棋安
 * on 2018/6/22.
 */
public class StoriedBuildingDetail_ActivityDemo extends ToolBarActivity {


    private static final int maxUploadFiles = 9;

    ImageView simpleDraweeView;

    NoScrollGridView atachInfoImage;


    private List<Attachement> selectedImgPathlistImage = new ArrayList<Attachement>(); //接收的附件信息

    private BaseAdapter attachInfoAdapter;
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

        simpleDraweeView = (ImageView) findViewById(R.id.my_image_view);

        Button button = (Button) findViewById(R.id.btn_uploads);

        atachInfoImage = ( NoScrollGridView ) findViewById( R.id.AtachInfoImage );



        Glide.with(this).load("http://pic27.photophoto.cn/20130515/0037037553414509_b.jpg").into(simpleDraweeView);


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

//                openImageSelect();
                openVedioSelect();
            }
        } );


        displayGridView();

        atachInfoImage.setAdapter(attachInfoAdapter);

        atachInfoImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent();
                ArrayList<GalleryFileInfo> urls = new ArrayList<GalleryFileInfo>();
                for (Attachement attach : selectedImgPathlistImage) {
                    if (attach.storageType.equals("2")) {
                        urls.add(new GalleryFileInfo(
                                GalleryFileType.Remote, attach.path));
                    } else {
                        urls.add(new GalleryFileInfo(GalleryFileType.Local,
                                attach.path));
                    }
                }
                Bundle mBundle = new Bundle();
//                mBundle.putSerializable(
//                        GalleryActivity.FilesUrlListParaName, urls);
//                intent.putExtras(mBundle);
//                intent.setClass(GongDanZhiXingExecute_Activity.this,
//                        GalleryActivity.class);
//                GongDanZhiXingExecute_Activity.this.startActivity(intent);

            }
        });



    }

    @Override
    public void initData() {

//        selectedImgPathlistImage.add( new Attachement( "","http://img1.imgtn.bdimg.com/it/u=511769507,3839834658&fm=27&gp=0.jpg","","" ) );
//        selectedImgPathlistImage.add( new Attachement( "","http://imgsrc.baidu.com/imgad/pic/item/34fae6cd7b899e51fab3e9c048a7d933c8950d21.jpg","","" ) );

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


                        Glide.with(getApplicationContext()).load("file://" +imageRadioResultEvent.getResult().getThumbnailSmallPath() ).into(simpleDraweeView);

                    }
                }).open();


    }


    private void openVedioSelect() {

        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(StoriedBuildingDetail_ActivityDemo.this);

        //设置自定义的参数
        instance
                .setType(RxGalleryFinalApi.SelectRXType.TYPE_VIDEO, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_RADIO)
                .setImageRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        ToastUtil.showToastShort( "视频选择" +imageRadioResultEvent.getResult().getThumbnailBigPath());


                        Glide.with(getApplicationContext()).load("file://" +imageRadioResultEvent.getResult().getThumbnailSmallPath() ).into(simpleDraweeView);

                    }
                }).open();

    }



    /**
     * 图片展示
     */
    private void displayGridView() {

        attachInfoAdapter = new BaseAdapter() {
            LayoutInflater inflater = null;

            @Override
            public int getCount() {
                if (selectedImgPathlistImage.size() < maxUploadFiles)

                    return selectedImgPathlistImage.size();
                return selectedImgPathlistImage.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Holder holder = null;
                if (convertView == null) {
                    inflater = LayoutInflater
                            .from(StoriedBuildingDetail_ActivityDemo.this);
                    convertView = inflater.inflate(
                            R.layout.activity_common_image_grid_item, parent,
                            false);

                    holder = new Holder();
                    holder.imageView = (ImageView) convertView
                            .findViewById(R.id.iv_imageView_display);

                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }

                if (position < selectedImgPathlistImage.size()) {

                    Attachement attach = selectedImgPathlistImage.get(position);
                    if ("1".equals(attach.storageType )) {
                        Bitmap myBitmap = BitmapUtils.tryGetBitmap(
                                selectedImgPathlistImage.get(position).path, 200,
                                200);
                        myBitmap = ThumbnailUtils.extractThumbnail(myBitmap,
                                100, 100);

                        holder.imageView.setImageBitmap(myBitmap);

                    } else {


                    }
                } else {
                    Drawable d = getResources().getDrawable(
                            R.mipmap.ic_addpic);
                    BitmapDrawable bd = (BitmapDrawable) d;
                    Bitmap myBitmap = bd.getBitmap();
                    myBitmap = ThumbnailUtils.extractThumbnail(myBitmap, 100,
                            100);
                    holder.imageView.setImageBitmap(myBitmap);
                    holder.imageView.setVisibility(View.VISIBLE);

                }

                return convertView;
            }

            class Holder {
                ImageView imageView;
            }
        };
    }

}
