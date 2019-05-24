package com.example.animapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.animapplication.activity.CustomSwitchingActivity;
import com.example.animapplication.activity.LayoutAnimationActivity;
import com.example.animapplication.activity.TypeEvaluatorActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Animation animation;

    private ImageView iv;
    private ListView lv;

    private String datas[]={ "alpha", "scale", "translate", "rotate",
            "逐帧", "LayoutAnimation", "PropertyAnimation","TypeEvaluator" };//准备数据源
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    private void initData() {
        iv.setImageResource(R.drawable.ic_launcher_background);
        // animation = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
        // 因为有动画链的原因，假定有一个移动的动画紧跟一个淡出的动画，
        // 如果不把移动的动画的setFillAfter置为true，那么移动动画结束后，View会回到原来的位置淡出，
        // 就像重置了一样，动画执行完，回到初始样式。
        // 如果setFillAfter置为true， 就会在移动动画结束的位置淡出
        // 也就是动画执行完，会保持动画执行后的样式。
        // animation.setFillAfter(true);
        // iv.startAnimation(animation);


        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datas);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        // 动态代码创建
//        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this,R.anim.zoom_in));
//        lac.setDelay(0.5f);
//        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);
//        lv.setLayoutAnimation(lac);
//        lv.setLayoutAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Toast.makeText(MainActivity.this,"end",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }

    private void initView() {
        iv=findViewById(R.id.iv_main_anim);
        lv=findViewById(R.id.lv_main);
    }

    private void setResource(int animResource){
        iv.setImageResource(R.drawable.ic_launcher_background);
        animation = AnimationUtils.loadAnimation(this,animResource);
        iv.startAnimation(animation);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                setResource(R.anim.anim_alpha);
                break;
            case 1:
                setResource(R.anim.anim_scale);
                break;
            case 2:
                setResource(R.anim.anim_translate);
                break;
            case 3:
                setResource(R.anim.anim_ratate);
                break;
            case 4:
                // 动态创建
                AnimationDrawable animationDrawable = new AnimationDrawable();
                animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 1000);
                animationDrawable.addFrame(getResources().getDrawable(R.drawable.img2), 1000);
                animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 1000);
                animationDrawable.addFrame(getResources().getDrawable(R.drawable.img2), 1000);
                animationDrawable.setOneShot(true);
                iv.setImageDrawable(animationDrawable);
                animationDrawable.start();

                // 静态创建
                // iv.setImageResource(R.drawable.anim_frame);
                // AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
                // animationDrawable.start();
                break;
            case 5:
                startActivity(new Intent(this,LayoutAnimationActivity.class));
                break;
            case 6:
                startActivity(new Intent(this,CustomSwitchingActivity.class));
                break;
            case 7:
                startActivity(new Intent(this,TypeEvaluatorActivity.class));
                break;
            default:
                break;
        }
    }
}
