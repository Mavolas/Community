package com.ziruk.oa.communitymodule.ui.StoriedBuilding;

/**
 * Created by 宋棋安
 * on 2018/6/15.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.adapter.listView.CommonListViewAdapter;
import com.ziruk.oa.communitymodule.adapter.listView.ViewHolder;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.ZirukHttpClient;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.callback.DisposeDataListener;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.exception.ZirukHttpException;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.mdel.ResponseCls;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.request.RequestParams;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.FilterBean;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.PurchaseInfo;
import com.ziruk.oa.communitymodule.uiCommon.fragment.bean.HomeSend;
import com.ziruk.oa.communitymodule.util.CurrentUserInfoUtils;
import com.ziruk.oa.communitymodule.util.ToastUtil;


import org.apache.commons.lang.time.DateFormatUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoriedBuildingList_ContentDemo extends Fragment {

    private Context mContext;

    private ListView mListView;
    private SmartRefreshLayout mRefreshLayout;

    private HomeSend mHomeSend;
    private FilterBean mFilterBean;

    private List<PurchaseInfo> mList;

    private MyAdapter myAdapter;

    private int pageIndex = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_gzsb_guzhang_shangbao_content, container, false);

        initView(view);

        initData(savedInstanceState);

        initListener();

        return view;
    }

    private void initView(View view) {

        //初始化EventBus
        EventBus.getDefault().register(this);

        mContext=view.getContext();

        mList=new ArrayList<>();
        mFilterBean=new FilterBean();


        mListView=(ListView) view.findViewById(R.id.lv_listView);
        mRefreshLayout=(SmartRefreshLayout)view.findViewById(R.id.refresh_Layout);


        myAdapter=new MyAdapter(mContext,mList,R.layout.activity_purchase_search_list_item);
        mListView.setAdapter(myAdapter);

    }

    private void initData(Bundle savedInstanceState) {


        if (savedInstanceState!=null){

            mHomeSend=(HomeSend)savedInstanceState.getSerializable("mHomeSend");

        }

        //默认检索三个月的数据
        Calendar cDayFrom = Calendar.getInstance();
        Calendar cDayTo = Calendar.getInstance();
        cDayFrom.setTime(new Date());
        cDayTo.setTime(new Date());
        cDayFrom.add(Calendar.MONTH, -3);
        cDayTo.add(Calendar.DAY_OF_MONTH, 1);

        mFilterBean.setDayFrom(cDayFrom.getTime());
        mFilterBean.setDayTo(cDayTo.getTime());

    }

    private void initListener() {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull  RefreshLayout refreshLayout) {

                mList.clear();
                LoadData(false);

            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                LoadData(true);
            }
        });

        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.autoRefresh();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onDataInital(HomeSend homeSend) {

        mHomeSend=homeSend;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataFilterReceive(FilterBean filterBean) {

        mList.clear();
        myAdapter.notifyDataSetChanged();

        mFilterBean=filterBean;
        mRefreshLayout.autoRefresh();

    }

    private void LoadData(boolean loadingMore){

        RequestParams params=new RequestParams();

        params.put("userid", CurrentUserInfoUtils.GetUserName(mContext));
        params.put("password",CurrentUserInfoUtils.getPassword(mContext));
        params.put("currentUser",CurrentUserInfoUtils.getGUID(mContext));
        params.put("SreenType",mHomeSend.ScreenType);
        params.put("depart",mFilterBean.getDepartment());
        params.put("type", mFilterBean.getStatus());
        params.put("Name",mFilterBean.getBidingName());
        params.put("Num",mFilterBean.getBidingCode());

        if (loadingMore){
            params.put("pageIndex",String.valueOf(pageIndex+1));
        }else {
            params.put("pageIndex",String.valueOf(0));
        }

        params.put("pageSize","10");

        if (mFilterBean.getDayFrom() != null)
            params.put("from",DateFormatUtils.format(mFilterBean.getDayFrom(), "yyyy/MM/dd"));
        if (mFilterBean.getDayTo() != null)
            params.put("to",DateFormatUtils.format(mFilterBean.getDayTo(), "yyyy/MM/dd"));


        ZirukHttpClient.newBuilder()
                .addParams(params)
                .setContext(mContext)
                .post().url("/Purchase/Index").build()
                .enqueue(new DisposeDataListener<ResponseCls<List<PurchaseInfo>>>() {

                    @Override
                    public void onSuccess(ResponseCls<List<PurchaseInfo>> response) {

                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();

                        if ("0".equals(response.getRequestStatus()))
                        {
                            if (response.getData() == null || response.getData().size()<=0) {
                                ToastUtil.showToastShort("未检索到数据！");
                            }
                            else {

                                mList.addAll(response.getData());
                                myAdapter.notifyDataSetChanged();
                                pageIndex = response.pageInfo.PageIndex;

                            }
                        }
                        else if ("1".equals(response.getRequestStatus())) {
                            ToastUtil.showToastShort("账号密码不正确，请退出系统重新登录！");
                        }
                        else {
                            ToastUtil.showToastShort("系统错误，请与管理员联系！");
                        }
                    }

                    @Override
                    public void onFailure(ZirukHttpException e) {
                        super.onFailure(e);

                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();

                        ToastUtil.showToastShort("无法连接至服务器！");
                    }

                });
    }



    public class MyAdapter extends CommonListViewAdapter<PurchaseInfo> {

        public MyAdapter(Context context, List<PurchaseInfo> datas, int layoutId) {

            super(context, datas, layoutId);
        }

        @Override
        public void Convert(ViewHolder holder, PurchaseInfo purchaseInfo) {

            TextView tv_Num = holder.getView(R.id.tv_Num);
            TextView tv_Type = holder.getView(R.id.tv_Type);
            TextView tv_Status = holder.getView(R.id.tv_Status);
            TextView tv_Price = holder.getView(R.id.tv_Price);
            TextView tv_Apart = holder.getView(R.id.tv_Apart);
            TextView tv_Method = holder.getView(R.id.tv_Method);
            TextView tv_Person = holder.getView(R.id.tv_Person);


            Map<String, String> TypeMap = new HashMap<String, String>();
            //[start] 状态赋值
            TypeMap.put("01", "邀请招标");
            TypeMap.put("02", "竞争性谈判");
            TypeMap.put("03", "询价");
            TypeMap.put("04", "单一来源");
            TypeMap.put("05", "公开招标");


            Map<String, String> StatusMap = new HashMap<String, String>();
            //[start] 状态赋值

            StatusMap.put("110", "招标书编制中");
            StatusMap.put("120", "招标书审批中");
            StatusMap.put("130", "招标书审批否决");
            StatusMap.put("140", "招标书审批通过");
            StatusMap.put("150", "招标公告已发布");
            StatusMap.put("160", "回标中");
            StatusMap.put("210", "开标时间未指定");
            StatusMap.put("220", "开标中");
            StatusMap.put("230", "已开标");
            StatusMap.put("310", "初步评审中");
            StatusMap.put("320", "详细评审中");
            StatusMap.put("330", "谈判中");
            StatusMap.put("335", "谈判结束");
            StatusMap.put("340", "等待二次报价");
            StatusMap.put("350", "评标结束");
            StatusMap.put("410", "定标报告待做成");
            StatusMap.put("420", "评标委员会未审定");
            StatusMap.put("430", "评标委员会已审定");
            StatusMap.put("440", "评标委员会未通过");
            StatusMap.put("450", "领导审核中");
            StatusMap.put("460", "领导审核通过");
            StatusMap.put("470", "领导审核驳回");
            StatusMap.put("480", "已发中标通告");
            StatusMap.put("Z00", "流标");


            tv_Num.setText(purchaseInfo.BiddingCode);
            tv_Type.setText(purchaseInfo.BiddingName);

            tv_Status.setText(StatusMap.get(purchaseInfo.BiddingStatus));

            DecimalFormat df = new DecimalFormat("###,###,###,###.00");

            tv_Price.setText(df.format(purchaseInfo.EstimatedAmount));

            tv_Method.setText(TypeMap.get(purchaseInfo.BiddingType));

            tv_Apart.setText(purchaseInfo.Department);

            tv_Person.setText(purchaseInfo.Transactor);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("mHomeSend",mHomeSend);
    }

}
