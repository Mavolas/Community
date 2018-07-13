package com.ziruk.oa.communitymodule.adapter.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 宋棋安
 * on 2018/6/15.
 */
public abstract class CommonListViewAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    protected Context mContext;
    protected int mLayoutId;


    public CommonListViewAdapter(Context context,List<T> datas,int layoutId ) {
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mContext = context;
        this.mLayoutId = layoutId ;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder=ViewHolder.get(mContext,convertView,parent,mLayoutId,position);

        Convert(holder,getItem(position));

        return holder.getConvertView();
    }

    /**
     * 实现绑定控件和值的逻辑
     * @param holder
     * @param t
     */
    public abstract void Convert(ViewHolder holder ,T t);


}
