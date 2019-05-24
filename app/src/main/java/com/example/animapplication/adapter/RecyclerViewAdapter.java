package com.example.animapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.animapplication.R;

import java.util.List;

/**
 * Created by xwxwaa on 2019/5/24.
 */

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mList;

    public RecyclerViewAdapter(Context mContext,List<String> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvText.setText(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListener.onItemListener(position);
            }
        });
    }

    @Override
    public int getItemCount() { return mList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;
        public ViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }
    /**
     * 回调
     */
    public static OnItemListener onItemListener;
    public interface OnItemListener {
        void onItemListener(int position);
    }
    public  void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
}
