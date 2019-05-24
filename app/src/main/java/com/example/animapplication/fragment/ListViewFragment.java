package com.example.animapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.animapplication.R;

/**
 * Created by xwxwaa on 2019/5/24.
 */

public class ListViewFragment extends Fragment{

    private ListView listView;
    private String datas[]={ "1", "2", "3", "4",
            "5", "6", "7","8" };//准备数据源
    private ArrayAdapter<String> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view,container,false);
        initView(view);
        initData(view);
        return view;
    }

    private void initView(View view) {
        listView = view.findViewById(R.id.lv_fragment);
    }

    private void initData(View view) {
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);
//         动态代码创建
        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.item_anim_listview));
        lac.setDelay(0.5f);
        // 按顺序显示
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        listView.setLayoutAnimation(lac);
        listView.setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getContext(),"end",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
