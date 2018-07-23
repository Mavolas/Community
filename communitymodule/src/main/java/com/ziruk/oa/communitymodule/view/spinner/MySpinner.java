package com.ziruk.oa.communitymodule.view.spinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.capabilities.zirukhttp.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MySpinner extends android.support.v7.widget.AppCompatEditText {

    private Context mContext = null;

    /** 当前选择值 */
    private String mCurrentSelectedItemvalue = "";
    private String mCurrentSelectedItemText = "";


    /** 选择项目后监听程序 */
    private MySpinner.OnItemSelectedListener mListenerSelected = null;
    private MySpinner.OnDataBindingBeginListener mListenerDataBind = null;

    private List<String> listDataSetValue = new ArrayList<String>();
    private List<String> listDataSetText = new ArrayList<String>();


    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        this.setCursorVisible(false);
        this.setFocusable(false);
        this.setSingleLine(true);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextThemeWrapper contextThemeWrapper =
                        new ContextThemeWrapper(mContext, R.style.Theme_AppCompat_Light_Dialog);
                AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
//				builder.setTitle("请点击选择颜色");
                builder.setItems(
                        listDataSetText.toArray(new String[] {}),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCurrentSelectedItemvalue = listDataSetValue.get(which);
                                if (this != null) {
                                    mCurrentSelectedItemText = listDataSetText.get(which);
                                    setText(mCurrentSelectedItemText);
                                }

                                if (mListenerSelected != null) {
                                    mListenerSelected.onItemSelected(which, mCurrentSelectedItemvalue);
                                }
                            }
                        });
                builder.setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mCurrentSelectedItemvalue = "";
                        if (this != null) {
                            mCurrentSelectedItemText = "";
                            setText(mCurrentSelectedItemText);
                        }

                        if (mListenerSelected != null) {
                            mListenerSelected.onNothingSelected();
                        }
                    }
                });
                builder.show();
            }
        });

        setWillNotDraw(false);
    }

    public MySpinner setOnItemSelectedListener(MySpinner.OnItemSelectedListener listener) {
        this.mListenerSelected = listener;
        return this;
    }

    public MySpinner setDataBindListener(MySpinner.OnDataBindingBeginListener listener) {
        this.mListenerDataBind = listener;
        return this;
    }

    public  void fresh() {
        fresh(false);
    }

    public void fresh(Boolean hasDefaultValue) {
        List<ValueTextCls> list = new ArrayList<ValueTextCls>();
        if (mListenerDataBind != null) {
            List<ValueTextCls> tmp = mListenerDataBind.getData();
            for (ValueTextCls item : tmp) {
                list.add(new ValueTextCls(
                        StringUtils.isEmpty(item.value) ? "" : item.value,
                        StringUtils.isEmpty(item.text) ? "" : item.text
                ));
            }
        }

        listDataSetValue.clear();
        listDataSetText.clear();

        for (ValueTextCls item : list) {
            listDataSetValue.add(item.value);
            listDataSetText.add(item.text);
        }

        if (hasDefaultValue==true && listDataSetValue.size()>0) {
            setValue(listDataSetValue.get(0));
        }
        else {
            setValue("");
        }
    }

    public interface OnDataBindingBeginListener {
        public List<ValueTextCls> getData();
    }
    public interface OnItemSelectedListener {
        public void onItemSelected(int position, String key);
        public void onNothingSelected();
    }

    public String getValue() {
        return mCurrentSelectedItemvalue;
    }

    public void setValue(String value) {
        mCurrentSelectedItemvalue = value;
        if (value==null || value.equals("")) {
            mCurrentSelectedItemText = "";
        }
        else if (listDataSetValue.contains(value)) {
            mCurrentSelectedItemText = listDataSetText.get(listDataSetValue.indexOf(value));
        }
        else {
            mCurrentSelectedItemText = "未定义的值";
        }

        if (this != null) setText(mCurrentSelectedItemText);
    }

    public void setValue(String value, String text) {
        mCurrentSelectedItemvalue = value;
        mCurrentSelectedItemText = text;
        if (this != null) setText(mCurrentSelectedItemText);
    }

}
