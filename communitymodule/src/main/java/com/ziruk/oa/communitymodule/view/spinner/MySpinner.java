package com.ziruk.oa.communitymodule.view.spinner;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ziruk.oa.communitymodule.R;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MySpinner  extends LinearLayout {
    //[start] 变量

    private Context mContext = null;

    private TextView mtv = null;

    /** 当前选择值 */
    private String mCurrentSelectedItemvalue = "";
    private String mCurrentSelectedItemText = "";

    private String mHintContent = "";
    private int mLayoutId = -1;
//	private int mTxtviewId = -1;

    /** 选择项目后监听程序 */
    private MySpinner.OnItemSelectedListener mListenerSelected = null;
    private MySpinner.OnDataBindingBeginListener mListenerDataBind = null;

    private List<String> listDataSetKey = new ArrayList<String>();
    private List<String> listDataSetText = new ArrayList<String>();

    private Boolean isFirstDraw = true;
    //[end]

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
//		int resouceId = -1;

        this.setOrientation(LinearLayout.HORIZONTAL);

        //[start] hint文本取得
        mHintContent = attrs.getAttributeValue(null, "hint");
        mLayoutId = attrs.getAttributeResourceValue(null, "layoutid", -1);
//		if (resouceId > 0) {
////			mLayoutId = context.getResources().getLayout(id).getDrawable(resouceId);
//		}
        //[end]

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextThemeWrapper contextThemeWrapper =
                        new ContextThemeWrapper(mContext, R.style.dialog);
                AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
//				builder.setTitle("请点击选择颜色");
                builder.setItems(
                        listDataSetText.toArray(new String[] {}),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCurrentSelectedItemvalue = listDataSetKey.get(which);
                                if (mtv != null) {
                                    mCurrentSelectedItemText = listDataSetText.get(which);
                                    mtv.setText(mCurrentSelectedItemText);
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
                        if (mtv != null) {
                            mCurrentSelectedItemText = "";
                            mtv.setText(mCurrentSelectedItemText);
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

    @Override
    protected void onDraw(Canvas canvas) {

        if (isFirstDraw==false)
            return;

        isFirstDraw = false;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(mLayoutId , null, false);

        int count = view.getChildCount();
        for (int i = 0; i < count; i++) {
            View item = view.getChildAt(i);
            if (item instanceof TextView) {
                mtv = (TextView) item;
                break;
            }
        }
        if (mHintContent != null && mtv != null)
            mtv.setHint(mHintContent);
        mtv.setText(mCurrentSelectedItemText);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        this.addView(view, lp2);
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
        List<CodeValueCls> list = new ArrayList<CodeValueCls>();
        if (mListenerDataBind != null) {
            List<CodeValueCls> tmp = mListenerDataBind.getData();
            for (CodeValueCls item : tmp) {
                list.add(new CodeValueCls(
                        StringUtils.isEmpty(item.key) ? "" : item.key,
                        StringUtils.isEmpty(item.text) ? "" : item.text
                ));
            }
        }

        listDataSetKey.clear();
        listDataSetText.clear();

        for (CodeValueCls item : list) {
            listDataSetKey.add(item.key);
            listDataSetText.add(item.text);
        }

        if (hasDefaultValue==true && listDataSetKey.size()>0) {
            setValue(listDataSetKey.get(0));
        }
        else {
            setValue("");
        }
    }

    public interface OnDataBindingBeginListener {
        public List<CodeValueCls> getData();
    }
    public interface OnItemSelectedListener {
        public void onItemSelected(int position, String key);
        public void onNothingSelected();
    }

    public String getValue() {
        return mCurrentSelectedItemvalue;
    }

    public String getText() {
        return mCurrentSelectedItemText;
    }

    public void setValue(String value) {
        mCurrentSelectedItemvalue = value;
        if (value==null || value.equals("")) {
            mCurrentSelectedItemText = "";
        }
        else if (listDataSetKey.contains(value)) {
            mCurrentSelectedItemText = listDataSetText.get(listDataSetKey.indexOf(value));
        }
        else {
            mCurrentSelectedItemText = "未定义的值";
        }

        if (mtv != null) mtv.setText(mCurrentSelectedItemText);
    }

    public void setValue(String value, String text) {
        mCurrentSelectedItemvalue = value;
        mCurrentSelectedItemText = text;
        if (mtv != null) mtv.setText(mCurrentSelectedItemText);
    }

    /**
     * 手动模式，值选择后不进行自动赋值及画面处理
     * @param manual
     */
    public void setManualMode(boolean manual) {

    }
}
