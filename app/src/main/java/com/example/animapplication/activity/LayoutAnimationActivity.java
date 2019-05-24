package com.example.animapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.animapplication.R;

/**
 * Created by xwxwaa on 2019/5/24.
 */

public class LayoutAnimationActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rgGroupview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);
        initView();
        initData();
        showFragment(0);
    }

    private void initData() {

    }

    private void initView() {
        rgGroupview = findViewById(R.id.rg_groupview);

        rgGroupview.setOnCheckedChangeListener(this);
    }

    /**
     * 通过反射来加载Fragment
     * currIndex 上一个fragment的序列号
     * showFragment() 展示Fragment
     */
    int currIndex = -1;
    Fragment[] fragmentList = new Fragment[3];

    public void showFragment(int index) {
        if (currIndex == index)
            return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (currIndex != -1) {
            ft.detach(fragmentList[currIndex]);
        }
        if (fragmentList[index] != null) {
            ft.attach(fragmentList[index]);
        } else {
            creatFragment(index);
            ft.replace(R.id.fl_fragment, fragmentList[index]);
        }
        if (currIndex != -1) {
            rgGroupview.getChildAt(currIndex).setSelected(false);
        }
        rgGroupview.getChildAt(index).setSelected(true);
        currIndex = index;
        ft.commit();
    }
    public String[] str = {"ListViewFragment","RecyclerViewFragment","GridViewFragment"};

    public void creatFragment(int index) {
        try {
            Fragment fragment = (Fragment) Class.forName("com.example.animapplication.fragment." + str[index]).newInstance();
            fragmentList[index] = fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_listview:
                showFragment(0);
                Toast.makeText(this,"ListView--",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_recyclerview:
                showFragment(1);
                Toast.makeText(this,"RecyclerView--",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_gridview:
                showFragment(2);
                Toast.makeText(this,"GridView--",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
