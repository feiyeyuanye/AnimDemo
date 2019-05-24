package com.example.animapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.animapplication.R;
import com.example.animapplication.evaluator.HsvEvaluator;
import com.example.animapplication.evaluator.PointFEvaluator;
import com.example.animapplication.view.CircleView;
import com.example.animapplication.view.ObjectAnimatorView;
import com.example.animapplication.view.OfObjectView;

/**
 * Created by xwxwaa on 2019/5/22.
 */

public class TypeEvaluatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvArgb,tvHsv,tvOfObject,tvHolder,tvAnimatorSet,tvKey;
    private ImageView ivHolder;
    private CircleView circleViewArgb;
    private OfObjectView ofObjectView;
    private ObjectAnimatorView viewKey;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_evaluator);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        circleViewArgb=findViewById(R.id.view_type_argb);
        ofObjectView=findViewById(R.id.view_type_ofObject);
        viewKey = findViewById(R.id.view_type_key);
        tvArgb=findViewById(R.id.tv_type_ofargb);
        tvHsv=findViewById(R.id.tv_type_ofHsv);
        tvOfObject=findViewById(R.id.tv_type_ofObject);
        tvHolder=findViewById(R.id.tv_type_holder);
        ivHolder = findViewById(R.id.iv_type_holder);
        tvAnimatorSet = findViewById(R.id.tv_type_AnimatorSet);
        tvKey = findViewById(R.id.tv_type_key);

        tvArgb.setOnClickListener(this);
        tvHsv.setOnClickListener(this);
        tvOfObject.setOnClickListener(this);
        tvOfObject.setOnClickListener(this);
        tvHolder.setOnClickListener(this);
        tvAnimatorSet.setOnClickListener(this);
        tvKey.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_type_ofargb:
                isShow(circleViewArgb);
                ObjectAnimator animator = ObjectAnimator.ofInt(circleViewArgb, "colors", 0xffff0000, 0xff00ff00);
                // 按照ARGB规则来变化，而不是按照一个整形int。
                animator.setEvaluator(new ArgbEvaluator());
                animator.setDuration(5000);
                animator.start();
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        circleViewArgb.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                circleViewArgb.setColors(Color.RED);
                            }
                        },500);
                    }
                });
//        在 Android 5.0 （API 21） 加入了新的方法 ofArgb()，如果minSdk 大于或者等于21，可直接用下面这种方式：
//        ObjectAnimator animator = ObjectAnimator.ofArgb(tvArgb, "color", 0xffff0000, 0xff00ff00);
//        animator.start();
                break;
            case R.id.tv_type_ofHsv:
                isShow(circleViewArgb);
                // 因为argb的方式，是计算机的方式，对人的感官而言是不直观的
                // 可以选择HSV(色相(Hue)，饱和度(Saturation)，明度(Value)),HSL(色相(Hue)，饱和度(Saturation)，亮度(Lightness))。
                ObjectAnimator animator1 = ObjectAnimator.ofInt(circleViewArgb, "colors", 0xff00ff00);
                // 使用自定义的 HslEvaluator
                animator1.setEvaluator(new HsvEvaluator());
                animator1.setDuration(5000);
                animator1.start();
                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        circleViewArgb.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                circleViewArgb.setColors(Color.RED);
                            }
                        },500);
                    }
                });
                break;
            case R.id.tv_type_ofObject:
                isShow(ofObjectView);
//                PointFEvaluator 这个类，所以 minSdk 大于或者等于 21 可以直接用，不用自己写了。
                ObjectAnimator animator2 = ObjectAnimator.ofObject(ofObjectView, "position",
                        new PointFEvaluator(), new PointF(0, 0), new PointF(1, 1));
                animator2.setInterpolator(new LinearInterpolator());
                animator2.setDuration(2000);
                animator2.start();
                break;
            case R.id.tv_type_holder:
                isShow(ivHolder);
                PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 0,1);
                PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 0,1);
                PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 0,1);
//              PropertyValuesHolder 是一个属性值的批量存放地。
//              使用 ofPropertyValuesHolder() 统一放进 Animator。
                ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(ivHolder, holder1, holder2, holder3);
                animator3.setDuration(2000);
                animator3.start();
                break;
            case R.id.tv_type_AnimatorSet:
                isShow(ivHolder);
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(ivHolder, "translationX", 0, -200);
                animator4.setDuration(1000);
                ObjectAnimator animator5 = ObjectAnimator.ofFloat(ivHolder, "alpha", 0, 1);
                animator5.setDuration(1000);
                ObjectAnimator animator6 = ObjectAnimator.ofFloat(ivHolder, "translationX", -200, 200);
                ObjectAnimator animator7 = ObjectAnimator.ofFloat(ivHolder, "rotation", 0, 1080);
                animator7.setDuration(1000);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(animator4).before(animator5); // 先执行 1 再执行 2
                animatorSet.playTogether(animator5,animator6, animator7); // 2 和 3 同时开始
                animatorSet.start();

                // 两个动画依次执行
//                animatorSet.playSequentially(animator4, animator5);
                // 两个动画同时执行
//                animatorSet.playTogether(animator1, animator2);
                // 使用 AnimatorSet.play(animatorA).with/before/after(animatorB)
                // 的方式来精确配置各个 Animator 之间的关系
//                animatorSet.play(animator1).with(animator2);
//                animatorSet.play(animator1).before(animator2);
//                animatorSet.play(animator1).after(animator2);
                break;
            case R.id.tv_type_key:
                isShow(viewKey);
                // 在 0% 处开始
                Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
                // 时间经过 50% 的时候，动画完成度 100%
                Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100);
                // 时间见过 100% 的时候，动画完成度倒退到 80%，即反弹 20%
                Keyframe keyframe3 = Keyframe.ofFloat(1, 80);
                PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);

                ObjectAnimator animator8 = ObjectAnimator.ofPropertyValuesHolder(viewKey, holder);
                animator8.setInterpolator(new FastOutSlowInInterpolator());
                animator8.setDuration(2000);
                animator8.start();
                break;
                default:
                    break;
        }
    }
    private View oldView;
    private void isShow(View view){
        if (oldView!=null){
            oldView.setVisibility(View.GONE);
        }
        view.setVisibility(View.VISIBLE);
        oldView = view;
    }
}
