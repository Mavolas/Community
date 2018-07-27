package com.ziruk.oa.communitymodule.ui.StoriedBuilding;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.ZirukHttpClient;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.callback.DisposeDataListener;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.exception.ZirukHttpException;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.model.ResponseCls;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.request.RequestParams;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.DepartInfo;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.FilterBean;
import com.ziruk.oa.communitymodule.util.CurrentUserInfoUtils;
import com.ziruk.oa.communitymodule.util.ToastUtil;
import com.ziruk.oa.communitymodule.view.spinner.ValueTextCls;
import com.ziruk.oa.communitymodule.view.spinner.MySpinner;


import org.apache.commons.lang.StringUtils;
import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StoriedBuildingList_FilterDemo extends Fragment {

    private Context mContext;

    private Button mBtnSubmit;
    private EditText mEtBidingName;
    private EditText mEtBidingCode;
    private MySpinner mMsStatus;
    private MySpinner mMsDepartment;
    private TextView mTvDayFrom;
    private RelativeLayout mRlDayFrom;
    private TextView mTvDayTo;
    private RelativeLayout mRlDayTo;

    private List<DepartInfo> mDepartList;

    private Calendar cDayFrom = Calendar.getInstance();
    private Calendar cDayTo = Calendar.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.activity_sbxj_xunjian_task_filter, container, false);

        initView(view);

        initData();

        initListener();

        return view;
    }

    private void initView(View view) {

        mDepartList=new ArrayList<>();
        mContext=view.getContext();

        mBtnSubmit=(Button) view.findViewById(R.id.btn_submit);
        mEtBidingName=(EditText) view.findViewById(R.id.et_bidingName);
        mEtBidingCode=(EditText) view.findViewById(R.id.et_bidingCode);
        mMsStatus=(MySpinner)view.findViewById(R.id.ms_status);
        mMsDepartment=(MySpinner)view.findViewById(R.id.ms_department);
        mTvDayFrom=(TextView) view.findViewById(R.id.tv_dayFrom);
        mRlDayFrom=(RelativeLayout)view.findViewById(R.id.rl_dayFrom);
        mTvDayTo=(TextView)view.findViewById(R.id.tv_dayTo);
        mRlDayTo=(RelativeLayout)view.findViewById(R.id.rl_dayTo);


        //region 设置默认检索区间显示

        cDayFrom.setTime(new Date());
        cDayTo.setTime(new Date());

        cDayFrom.add(Calendar.MONTH, -3);  //默认检索三个月的数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        mTvDayFrom.setText(sdf.format(cDayFrom.getTime()));
        mTvDayTo.setText(sdf.format(cDayTo.getTime()));

        //endregion


    }

    private void initData(){

        RequestParams params=new RequestParams();

        params.put("userid", CurrentUserInfoUtils.GetUserName(mContext));
        params.put("password",CurrentUserInfoUtils.getPassword(mContext));
        params.put("currentUser",CurrentUserInfoUtils.getGUID(mContext));


        ZirukHttpClient.newBuilder()
                .addParams(params)
                .setContext(mContext)
                .post().url("/Purchase/Department").build()
                .enqueue(new DisposeDataListener<ResponseCls<List<DepartInfo>>>() {

                    @Override
                    public void onSuccess(ResponseCls<List<DepartInfo>> response) {

                        if ("0".equals(response.getRequestStatus()))
                        {
                            if (response.getData() == null || response.getData().size()<=0) {
//								ToastUtil.showToastShort("未检索到数据！");
                            }
                            else {
                                mDepartList.addAll(response.getData());

                                mMsDepartment.setDataBindListener(new MySpinner.OnDataBindingBeginListener() {
                                    @Override
                                    public List<ValueTextCls> getData() {

                                        List<ValueTextCls> list = new ArrayList<ValueTextCls>();

                                        for (DepartInfo DepartList : mDepartList) {
                                            list.add(new ValueTextCls(DepartList.ID, DepartList.Name));
                                        }

                                        return list;
                                    }
                                });

                                mMsDepartment.fresh();
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

                        ToastUtil.showToastShort("无法连接至服务器！");
                    }

                });
    }

    private void initListener() {

        mMsStatus.setDataBindListener(new MySpinner.OnDataBindingBeginListener() {
            @Override
            public List<ValueTextCls> getData() {

                List<ValueTextCls> list = new ArrayList<ValueTextCls>();
                list.add(new ValueTextCls("110", "招标书编制中"));
                list.add(new ValueTextCls("120", "招标书审批中"));
                list.add(new ValueTextCls("130", "招标书审批否决"));
                list.add(new ValueTextCls("140", "招标书审批通过"));
                list.add(new ValueTextCls("150", "招标公告已发布"));

                list.add(new ValueTextCls("160", "回标中"));
                list.add(new ValueTextCls("220", "开标中"));
                list.add(new ValueTextCls("230", "已开标"));
                list.add(new ValueTextCls("310", "初步评审中"));
                list.add(new ValueTextCls("320", "详细评审中"));

                list.add(new ValueTextCls("330", "谈判中"));
                list.add(new ValueTextCls("335", "谈判结束"));
                list.add(new ValueTextCls("340", "等待二次报价"));
                list.add(new ValueTextCls("350", "评标结束"));
                list.add(new ValueTextCls("410", "定标报告待做成"));

                list.add(new ValueTextCls("420", "评标委员会未审定"));
                list.add(new ValueTextCls("430", "评标委员会已审定"));
                list.add(new ValueTextCls("440", "评标委员会未通过"));
                list.add(new ValueTextCls("450", "领导审核中"));
                list.add(new ValueTextCls("460", "领导审核通过"));

                list.add(new ValueTextCls("470", "领导审核驳回"));
                list.add(new ValueTextCls("480", "已发中标通告"));
                list.add(new ValueTextCls("Z00", "流标"));
                //[start] 状态赋值

                return list;
            }
        });

        mMsStatus.fresh();

        mRlDayFrom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new DatePickerDialog(
                        mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                cDayFrom.set(year, month, dayOfMonth);

                                String text = year + "/"
                                        + StringUtils.leftPad(String.valueOf(month+1), 2, '0')
                                        + "/" + StringUtils.leftPad(String.valueOf(dayOfMonth), 2, '0');
                                mTvDayFrom.setText(text);
                            }
                        },
                        cDayFrom.get(Calendar.YEAR), // 传入年份
                        cDayFrom.get(Calendar.MONTH), // 传入月份
                        cDayFrom.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                dialog.show();

            }
        });
        mRlDayTo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new DatePickerDialog(
                        mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                cDayTo.set(year, month, dayOfMonth);

                                String text = year + "/"
                                        + StringUtils.leftPad(String.valueOf(month+1), 2, '0')
                                        + "/" + StringUtils.leftPad(String.valueOf(dayOfMonth), 2, '0');
                                mTvDayTo.setText(text);
                            }
                        },
                        cDayTo.get(Calendar.YEAR), // 传入年份
                        cDayTo.get(Calendar.MONTH), // 传入月份
                        cDayTo.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                dialog.show();

            }
        });

        mBtnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterBean filterBean=new FilterBean();

                filterBean.setBidingName(mEtBidingName.getText().toString());
                filterBean.setBidingCode(mEtBidingCode.getText().toString());
                filterBean.setStatus(mMsStatus.getValue());
                filterBean.setDepartment(mMsDepartment.getValue());
                filterBean.setDayFrom(cDayFrom.getTime());
                filterBean.setDayTo(cDayTo.getTime());

                EventBus.getDefault().post(filterBean);

                StoriedBuildingList_ActivityDemo.CloseDrawer();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

