package com.example.animapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RadioGroup;

import com.example.animapplication.R;
import com.example.animapplication.adapter.GridViewadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwxwaa on 2019/5/24.
 */

public class GridViewFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private GridView gridView;
    private GridViewadapter gridViewadapter ;
    private List<String> mList;
    private RadioGroup rgGrid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid_view,container,false);
        initView(view);
        initData(view);
        return view;
    }

    private void initData(View view) {
        mList = new ArrayList<>();
        for (int i=0;i<39;i++){
            mList.add(String.valueOf(i));
        }

        gridViewadapter = new GridViewadapter(getContext(),mList);
        gridView.setAdapter(gridViewadapter);
        showAnim(R.anim.item_anim_gridview_layout);
    }

    private void initView(View view) {
        gridView = view.findViewById(R.id.gv_fragment);
        rgGrid = view.findViewById(R.id.rg_grid);

        rgGrid.setOnCheckedChangeListener(this);
    }

    /**
     * 前两个layout用的是同一个效果
     * 只不过第一个是 layoutAnimation，第二个是 gridLayoutAnimation
     * 所以，对于 GridView，使用 gridLayoutAnimation 的效果更好些。
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_grid_one:
                showAnim(R.anim.item_anim_gridview_layout4);
                break;
            case R.id.rb_grid_two:
                showAnim(R.anim.item_anim_gridview_layout);
                break;
            case R.id.rb_grid_thr:
                showAnim(R.anim.item_anim_gridview_layout2);
                break;
            case R.id.rb_grid_fou:
                showAnim(R.anim.item_anim_gridview_layout3);
                break;
                default:
                    break;
        }
    }
    private void showAnim(int id){
        int resId = id;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        gridView.setLayoutAnimation(animation);
        gridViewadapter.notifyDataSetChanged();
    }
}
