package com.ziruk.oa.communitymodule.ui.StoriedBuilding;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.base.activity.ToolBarActivity;
import com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean.SpinnerOption;
import com.ziruk.oa.communitymodule.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 宋棋安
 * on 2018/6/22.
 */
public class StoriedBuildingDetail_ActivityDemo extends ToolBarActivity {
    @Override
    public void setHeader() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_building_detail;
    }

    @Override
    public void initViews() {

        final Spinner spinner = (Spinner) findViewById(R.id.sp_test);


//        List<ValueTextCls> list = new ArrayList<ValueTextCls>();
//        ValueTextCls spinnerItem=new ValueTextCls("1", "测试数据1");
//        ValueTextCls spinnerItem2=new ValueTextCls("2", "测试数据2");
//        list.add(spinnerItem);
//        list.add(spinnerItem2);

         List<SpinnerOption> list = new ArrayList<SpinnerOption>();

        list.add(new SpinnerOption("我是0","全部"));

        list.add(new SpinnerOption("我是1","1"));
        list.add(new SpinnerOption("我是2","2"));
        list.add(new SpinnerOption("我是3","3"));
        list.add(new SpinnerOption("我是4","4"));


        ArrayAdapter<SpinnerOption> adapter= new ArrayAdapter<SpinnerOption>(StoriedBuildingDetail_ActivityDemo.this,
                android.R.layout.simple_list_item_1, list);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtil.showToastShort(((SpinnerOption)spinner.getSelectedItem()).getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

}
