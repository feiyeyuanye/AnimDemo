package com.example.animapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.animapplication.R;
import com.example.animapplication.adapter.QuickAdapter;
import com.example.animapplication.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwxwaa on 2019/5/24.
 */

public class RecyclerViewFragment  extends Fragment implements RecyclerViewAdapter.OnItemListener {

    private RecyclerView rvList;
    private RecyclerViewAdapter viewAdapter;
    private List<String> mList ;
    private QuickAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view,container,false);
        initView(view);
        initData(view);
        return view;
    }



    private void initData(View view) {
        // 测试数据
        mList= new ArrayList<>();
        for (int i = 0;i<8;i++){
            mList.add(String.valueOf(i));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        rvList.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        // 使用传统适配器
//        viewAdapter = new RecyclerViewAdapter(getContext(),mList);
//        viewAdapter.setOnItemListener(this);
//        //设置Adapter
//        rvList.setAdapter(viewAdapter);

        // 使用万能适配器
        mAdapter = new QuickAdapter<String>(mList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recyclerview;
            }

            @Override
            public void convert(VH holder, String data, int position) {
                holder.setText(R.id.tv_text, data);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setData();
                    }
                });
            }
            // 复杂情况
//            @Override
//            public int getLayoutId(int viewType) {
//                switch(viewType){
//                    case TYPE_1:
//                        return R.layout.item_1;
//                    case TYPE_2:
//                        return R.layout.item_2;
//                }
//            }
//
//            @Override
//            public int getItemViewType(int position) {
//                if(position % 2 == 0){
//                    return TYPE_1;
//                } else{
//                    return TYPE_2;
//                }
//            }
//
//            @Override
//            public void convert(VH holder, Model data, int position) {
//                int type = getItemViewType(position);
//                switch(type){
//                    case TYPE_1:
//                        holder.setText(R.id.text, data.text);
//                        break;
//                    case TYPE_2:
//                        holder.setImage(R.id.image, data.image);
//                        break;
//                }
//            }
        };
        viewAdapter = new RecyclerViewAdapter(getContext(),mList);
        viewAdapter.setOnItemListener(this);
        //设置Adapter
        rvList.setAdapter(mAdapter);
        //设置分隔线
        rvList.addItemDecoration(new DividerItemDecoration(getContext(),1));
        //设置增加或删除条目的动画
//        rvList.setItemAnimator( new DefaultItemAnimator());

        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getContext(), R.anim.item_anim_recyclerview_layout);
        rvList.setLayoutAnimation(controller);
    }
    private void initView(View view) {
        rvList=view.findViewById(R.id.rv_list);
    }

    /**
     * 修改列表的数据
     */
    private void setData(){
        mList.add(String.valueOf(mList.size()));
        viewAdapter.setmList(mList);
        // 数据变化时，触发动画
        rvList.getAdapter().notifyDataSetChanged();
        // 播放动画
        rvList.scheduleLayoutAnimation();
    }

    /**
     * 传统适配器的item点击监听
     */
    @Override
    public void onItemListener(int position) {
        setData();
    }
}
