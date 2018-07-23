package com.ziruk.oa.communitymodule.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.ziruk.oa.communitymodule.R;
import com.ziruk.oa.communitymodule.view.spinner.ValueTextCls;

public class MyDateTimePicker  extends android.support.v7.widget.AppCompatEditText {
	
	private Context mContext = null;

	/** 当前选择值 */
	private Date mCurrentSelectedItemvalue = null;
	private String mCurrentSelectedItemText = "";

	private String mDateFormat = "yyyy-MM-dd hh:mm:ss";
	private Boolean mYearMonthOnly = false;
	private Boolean mTimeOnly = false;
	
	/** 选择项目后监听程序 */
	private OnItemSelectedListener mListenerSelected = null;

	public MyDateTimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mContext = context;

		this.setCursorVisible(false);
		this.setFocusable(false);
		this.setSingleLine(true);

		TypedArray typedArray=context.obtainStyledAttributes( attrs, R.styleable.MyDateTimePicker);

		try{

			String DateFormat = typedArray.getString( R.styleable.MyDateTimePicker_dateFormat);
			boolean YearMonthOnly = typedArray.getBoolean( R.styleable.MyDateTimePicker_yearMonthOnly,false);
			boolean TimeOnly = typedArray.getBoolean( R.styleable.MyDateTimePicker_timeOnly,false);

			mYearMonthOnly=YearMonthOnly;
			mTimeOnly=TimeOnly;
			mDateFormat =DateFormat;

			if(DateFormat==null){
				mDateFormat = "yyyy-MM-dd hh:mm";
			}


		}finally {

			typedArray.recycle();

		}


		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				final Calendar cDay = Calendar.getInstance();
				if (mCurrentSelectedItemvalue!=null)
					cDay.setTime(mCurrentSelectedItemvalue);  
				
				if ( mYearMonthOnly == true) {
			        Dialog dialog = new DatePickerDialog(
			        		mContext,
			                new DatePickerDialog.OnDateSetListener() {
			                	boolean mFired = false;
			                    public void onDateSet(DatePicker dp, final int year, final int month, final int dayOfMonth) {
			                    	if (mFired == true)
			                    		return;                           	
			                    	mFired = true;
			                    	
			                        cDay.set(year, month, dayOfMonth);
	
			                        setValue(cDay.getTime());
			                    }
			                },
			                cDay.get(Calendar.YEAR), // 传入年份
			                cDay.get(Calendar.MONTH), // 传入月份
			                cDay.get(Calendar.DAY_OF_MONTH) // 传入天数
			        );
			        dialog.show();
				}
				else if (mTimeOnly == true) {
                    Dialog dialogTime = new TimePickerDialog(
                    		mContext,
                            new TimePickerDialog.OnTimeSetListener() {
                                public void onTimeSet(TimePicker var1, int var2, int var3) {
                                	cDay.set(cDay.get(Calendar.YEAR), cDay.get(Calendar.MONTH), cDay.get(Calendar.DAY_OF_MONTH), var2, var3);
                                	setValue(cDay.getTime());
                                }
                            },
                            cDay.get(Calendar.HOUR_OF_DAY),
                            cDay.get(Calendar.MINUTE),
                            true
                            );
                    dialogTime.show();
				}
				else {
			        Dialog dialog = new DatePickerDialog(
			        		mContext,
			                new DatePickerDialog.OnDateSetListener() {
			                	boolean mFired = false;
			                    public void onDateSet(DatePicker dp, final int year, final int month, final int dayOfMonth) {
			                    	if (mFired == true)
			                    		return;                           	
			                    	mFired = true;
			                    	
			                        cDay.set(year, month, dayOfMonth);
	
			                        final String tmpDay = year + "/" 
			                        		+ StringUtils.leftPad(String.valueOf(month+1), 2, '0')   
			                        		+ "/" + StringUtils.leftPad(String.valueOf(dayOfMonth), 2, '0');
			                        
			                        Dialog dialogTime = new TimePickerDialog(
			                        		mContext,
			                                new TimePickerDialog.OnTimeSetListener() {
			                                    public void onTimeSet(TimePicker var1, int var2, int var3) {
			                                    	cDay.set(year, month, dayOfMonth, var2, var3);
			                                    	setValue(cDay.getTime());
			                                    }
			                                },
			                                cDay.get(Calendar.HOUR_OF_DAY),
			                                cDay.get(Calendar.MINUTE),
			                                true
			                                );
			                        dialogTime.show();
	
			                    }
			                },
			                cDay.get(Calendar.YEAR), // 传入年份
			                cDay.get(Calendar.MONTH), // 传入月份
			                cDay.get(Calendar.DAY_OF_MONTH) // 传入天数
			        );
			        dialog.show();
				}

			}
		});
	
		setWillNotDraw(false);
	}

	public MyDateTimePicker setOnItemSelectedListener(OnItemSelectedListener listener) {
		this.mListenerSelected = listener;
		return this;
	}
	
	public interface OnDataBindingBeginListener {
		public List<ValueTextCls> getData();
	}

	public interface OnItemSelectedListener {
		public void onItemSelected(int position, String key);
		public void onNothingSelected();
	}
	
	public String getValue(String pattern) {
		return DateFormatUtils.format(this.mCurrentSelectedItemvalue, pattern);
	}
	public Date getValue() {
		return this.mCurrentSelectedItemvalue;
	}

	public void setValue(Date value) {
		mCurrentSelectedItemvalue = value;
		if (value==null || value.equals("")) {
			mCurrentSelectedItemText = "";
		}
		else {
			mCurrentSelectedItemText = DateFormatUtils.format(this.mCurrentSelectedItemvalue, mDateFormat );
		}
		
		if (this != null) this.setText(mCurrentSelectedItemText);
	}

	public void setValue(String value, String pattern) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			mCurrentSelectedItemvalue = sdf.parse(value);

			setValue(mCurrentSelectedItemvalue);
		}
		catch (ParseException e) {

		}
	}

}
