package com.example.animapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.animapplication.R;

import java.util.List;

/**
 * Created by xwxwaa on 2019/5/24.
 */

public class GridViewadapter extends BaseAdapter{

    private Context mContext;
    private List<String> mList;
    public GridViewadapter(Context mContext,List<String> mList){
        this.mContext=mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = View.inflate(mContext, R.layout.item_gridview,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.tv_text);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(mList.get(i));
        return view;
    }

    public class ViewHolder{
        TextView textView;
    }
}
