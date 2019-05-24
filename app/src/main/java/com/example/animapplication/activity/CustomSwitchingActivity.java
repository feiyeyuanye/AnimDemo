package com.example.animapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animapplication.R;
import com.example.animapplication.utils.ReverseInterpolator;
import com.example.animapplication.view.ViewWrapper;

/**
 * Created by xwxwaa on 2019/5/20.
 */

public class CustomSwitchingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    /**
     * 使用属性动画 ObjectAnimator
     * 来实现补间动画的四种效果
     */
    private TextView tvAlpha,tvScale,tvTranslate,tvRotate;

    /**
     * 自封装
     * 提供get和set方法
     */
    private TextView tvWrapper;

    /**
     * 属性动画 ValueAnimator
     * 实现搜索框的显示和隐藏
     */
    private EditText etSearch;
    private Button btSearch;
    private int etWidth;

    /**
     * ViewGroup添加子View的过渡动画
     */
    private LinearLayout llImgs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.custom_in,0);
        setContentView(R.layout.activity_custom_switching);
        initView();
        initData();
    }

    private void initData() {
        iv.setImageResource(R.drawable.ic_launcher_background);

        // 获取搜索栏的宽度
        etSearch.post(new Runnable() {
            @Override
            public void run() {
                etWidth=etSearch.getWidth();
            }
        });

        // 为ViewGroup的子View添加过渡动画
        imgsTransition();

    }

    /**********************************************************************************************
     * 通过不断平移
     * 来实现动画效果
     */
    float newTranslationX ;
    public void translationX(View view){
        newTranslationX = 0;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 每次向右移动3像素，100次就是300像素。
                newTranslationX += 3 ;
                iv.setTranslationX(newTranslationX);
                if (newTranslationX == 300){
                    // 重置
                    iv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iv.setTranslationX(0);
                        }
                    }, 500);
                }
            }
        };
        for (int i =0;i<100;i++){
            // 每10毫秒改变一次位置，f反复100次。
            iv.postDelayed(runnable,i*10);
        }
    }

    /**
     * View.animate()
     * ViewPropertyAnimator不支持重复
     */
    public void viewAnimate(View view){
        ViewPropertyAnimator vpa = iv.animate();
        vpa.alpha(0.5f);
        // 每隔10毫秒，调用一次view.setTranslationX()方法。
        vpa.translationX(-300);
        // vpa.translationXBy(-100);
        Path interpolatorPath = new Path();
        // 先以「动画完成度 : 时间完成度 = 1 : 1」的速度匀速运行 25%
        interpolatorPath.lineTo(0.25f, 0.25f);
        // 然后瞬间跳跃到 150% 的动画完成度
        interpolatorPath.moveTo(0.25f, 1.5f);
        // 再匀速倒车，返回到目标点
        interpolatorPath.lineTo(1, 1);
        vpa.setInterpolator(PathInterpolatorCompat.create(interpolatorPath));
//      vpa.setInterpolator(new BounceInterpolator());
        vpa.setDuration(2000);
        vpa.withStartAction(new Runnable() {
            @Override
            public void run() {
                // 一次性的监听方法
                Toast.makeText(CustomSwitchingActivity.this,"动画开始",Toast.LENGTH_SHORT).show();
            }
        });
                vpa.withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CustomSwitchingActivity.this,"动画结束",Toast.LENGTH_SHORT).show();
                        // 一次性的监听方法
                        // 只在动画正常结束时才会被调用，而在动画被取消时不会被执行
                        iv.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iv.setTranslationX(0);
                            }
                        }, 500);
                    }
                });


    }


    /**********************************************************************************************/

    /**********************************************************************************************
     * 添加图片
     */
    public void imgAdd(View view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,10,0);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(layoutParams);

        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(200,200);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(imgParams);

        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this);
        tvParams.gravity = Gravity.RIGHT|Gravity.BOTTOM;
        tv.setText(String.valueOf(llImgs.getChildCount()+1));
        tv.setLayoutParams(tvParams);

        frameLayout.addView(imageView);
        frameLayout.addView(tv);

        llImgs.addView(frameLayout, llImgs.getChildCount());
    }

    /**
     * 移除图片
     */
    public void imgRemove(View view){
        int count = llImgs.getChildCount();
        if (count > 0) {
            llImgs.removeViewAt(llImgs.getChildCount()-1);
        }
    }

    private void imgsTransition() {
//        APPEARING
//        当通过 设置子View的可见性为VISIBLE或者通过addView方法添加子View 来显示子View时，
//        子View就会执行该类型的动画。
//        该类型动画的周期为300毫秒，默认延迟为300毫秒。
//
//        DISAPPEARING
//        当通过 设置子View的可见性为GONE或者通过removeView方法移除子View 来隐藏子View时，
//        子View就会执行该类型的动画。
//        该类型动画的周期为300毫秒，默认延迟为0毫秒。
//
//        CHANGE_APPEARING
//        当显示子View时，所有的兄弟View就会立即依次执行该类型动画并且兄弟View之间执行动画的间隙默认为0毫秒，然后才会执行显示子View的动画。
//        该类型动画的周期为300毫秒，默认延迟为0毫秒。
//
//        CHANGE_DISAPPEARING
//        当隐藏子View的动画执行完毕后，所有的兄弟View就会依次执行该类型动画并且兄弟View之间执行动画的间隙默认为0毫秒。
//        该类型动画的周期为300毫秒，默认延迟为300毫秒。
//
//        注意 上面描述的都是系统默认的行为，我们可以做适当的改变。
//        依次对CHANGE_APPEARING、APPEARING、DISAPPEARING 和 CHANGE_DISAPPEARING 类型的过渡动画进行设置
        LayoutTransition transition = new LayoutTransition();

        // 当多个子View要执行同一个类型的动画时，就可以通过该方法来设置子View之间执行动画的间隙，默认为0毫秒。
        transition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        // 为指定类型的过渡动画设置执行动画的周期，默认为300毫秒。
        transition.setDuration(LayoutTransition.CHANGE_APPEARING, transition.getDuration(LayoutTransition.CHANGE_APPEARING));
        // 为指定类型的过渡动画设置延迟执行的时间，默认与过渡动画的类型相关。
        transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);

        ObjectAnimator appearingAnimator = ObjectAnimator.ofPropertyValuesHolder(
                        (Object) null,
                        PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                        PropertyValuesHolder.ofFloat("alpha", 0, 1.0f));
        // 为指定类型的过渡动画设置自定义的属性动画。
        transition.setAnimator(LayoutTransition.APPEARING, appearingAnimator);
        transition.setDuration(LayoutTransition.APPEARING, transition.getDuration(LayoutTransition.APPEARING));
        transition.setStartDelay(LayoutTransition.APPEARING, transition.getDuration(LayoutTransition.CHANGE_APPEARING));

        ObjectAnimator disappearingAnimator = ObjectAnimator
                .ofPropertyValuesHolder(
                        (Object) null,
                        PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f),
                        PropertyValuesHolder.ofFloat("alpha", 1.0f, 0));
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearingAnimator);
        transition.setDuration(LayoutTransition.DISAPPEARING, transition.getDuration(LayoutTransition.DISAPPEARING));
        transition.setStartDelay(LayoutTransition.DISAPPEARING, 0);

        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        transition.setDuration(LayoutTransition.CHANGE_DISAPPEARING, transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        transition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, transition.getDuration(LayoutTransition.DISAPPEARING));

        // setLayoutTransition方法,为ViewGroup设置过渡动画。
        llImgs.setLayoutTransition(transition);
    }

    /**********************************************************************************************/

     /**********************************************************************************************
     * 控制搜索栏的显示
     */
    public void search(View view){
        btSearch.setEnabled(false);
        if (etSearch.getVisibility() == View.GONE){
            // 显示搜索栏
            animateShow(etSearch);
        }else {
            // 隐藏搜索栏
            animateHide(etSearch);
        }
    }

    /**
     * 隐藏
     */
    private void animateHide(final View view) {
        ValueAnimator va = createDropAnimator(view,etWidth,0);
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                btSearch.setEnabled(true);
            }
        });
        va.setInterpolator(new DecelerateInterpolator());
        va.setDuration(1000);
        va.start();
    }
    /**
     * 显示
     */
    private void animateShow(final View view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator va = createDropAnimator(view,0,etWidth);
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btSearch.setEnabled(true);
            }
        });
        va.setInterpolator(new AccelerateInterpolator());
        va.setDuration(1000);
        va.start();
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end){
        ValueAnimator va = ValueAnimator.ofInt(start,end);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                params.width = (int) valueAnimator.getAnimatedValue();
                view.setLayoutParams(params);
            }
        });
        return va;
    }
    /**********************************************************************************************/

     @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_custom_alpha:
                ObjectAnimator ob = ObjectAnimator.ofFloat(iv,"alpha",1,0,1).setDuration(2000);
                ob.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float value = (Float) valueAnimator.getAnimatedValue();
                                Log.e("TAG", value + "");
                                if (value == 1){
                                    Toast.makeText(CustomSwitchingActivity.this,"执行结束",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                // 延迟1000ms后执行,需要在start()前调用
                ob.setStartDelay(1000);
                ob.start();
                break;
            case R.id.tv_custom_scale:
                final AnimatorSet as = new AnimatorSet();
                iv.setPivotX(iv.getWidth()/2);
                iv.setPivotY(iv.getHeight()/2);
                as.playTogether(
                        ObjectAnimator.ofFloat(iv,"scaleX",1,0).setDuration(2000),
                        ObjectAnimator.ofFloat(iv,"scaleY",1,0).setDuration(2000)
                );
                //添加监听事件
                as.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        // 动画开始的时候调用
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        // 动画结束的时候调用
                        Toast.makeText(CustomSwitchingActivity.this,"执行结束,重置动画",Toast.LENGTH_SHORT).show();
                        // 重置动画
                        as.removeListener(this);
                        as.setDuration(0);
                        as.setInterpolator(new ReverseInterpolator());
                        as.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        // 动画被取消的时候调用

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        // 动画重复执行的时候调用

                    }
                });
                as.start();
                break;
            case R.id.tv_custom_translate:
                final AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(iv,"translationX",20,100).setDuration(2000),
                        ObjectAnimator.ofFloat(iv,"translationY",20,100).setDuration(2000)
                );
                //另一种设置监听的方式，里面的监听方法可以选择性重写
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Toast.makeText(CustomSwitchingActivity.this,"执行结束",Toast.LENGTH_SHORT).show();
                        // 重置动画
                        animatorSet.removeListener(this);
                        animatorSet.setDuration(0);
                        animatorSet.setInterpolator(new ReverseInterpolator());
                        animatorSet.start();
                    }
                });
                animatorSet.start();
                break;
            case R.id.tv_custom_rotate:
                iv.setPivotX(iv.getWidth()/2);
                iv.setPivotY(iv.getHeight()/2);
                ObjectAnimator.ofFloat(iv,"rotation",0,360).setDuration(2000).start();
                break;
            case R.id.tv_custom_wrapper:
//  无法使用属性动画或者属性动画不起作用的情况：
//  1.该字段没有set和get方法
//  2.该属性的set方法仅仅改变了对象的属性值，但是没有将这种改变用动画的形式表现出来
//  解决方法：
//  1.如果有权限的话，给这个字段添加get和set方法，比如在自定义View中。
//  2.使用一个包装类来封装该字段对应的类，间接为该字段提供get和set方法。
//  3.采用ValueAnimator，监听动画过程，自己实现属性的改变
//  关于ValueAnimator，其本身不作用于任何对象，也就是说直接使用它没有任何动画效果。
//  它可以对一个值做动画，然后我们可以监听其动画过程，在动画过程中修改我们的对象的属性值，这样也就相当于我们的对象做了动画。

                // 改变ImageView宽度，采用第二种方式。
                ViewWrapper vw = new ViewWrapper(iv);
                ObjectAnimator.ofInt(vw,"width",20).setDuration(2000).start();

                // 改变ImageView宽度，采用第三种方式。
//                performAnimate(iv, iv.getWidth(), 20);
                break;
                default:
                    break;
        }
    }

    private void performAnimate(final View target, final int start, final int end) {
//        关于这个ValueAnimator，它会在2000ms内将一个数从1变到100，然后动画的每一帧会回调onAnimationUpdate方法，
//        在这个方法里，我们可以获取当前的值（1-100），根据当前值所占的比例（当前值/100），
//        我们可以计算出现在的宽度应该是多少，比如时间过了一半，当前值是50，比例为0.5，
//        假设起始宽度是100px，最终宽度是500px，那么增加的宽度也应该占总增加宽度的一半，
//        总增加宽度是500-100=400，所以这个时候应该增加宽度400*0.5=200，
//        那么当前的宽度应该为初始宽度+ 增加宽度（100+200=300）。
//        上述计算过程很简单，其实它就是整型估值器IntEvaluator的内部实现。
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer)animator.getAnimatedValue();

                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;

                //这里刚哥偷懒了，不过有现成的干吗不用呢（确实）
                //直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(2000).start();
    }

    private void initView() {
        iv=findViewById(R.id.iv_custom_anim);
        tvAlpha=findViewById(R.id.tv_custom_alpha);
        tvScale=findViewById(R.id.tv_custom_scale);
        tvTranslate=findViewById(R.id.tv_custom_translate);
        tvRotate=findViewById(R.id.tv_custom_rotate);

        tvWrapper=findViewById(R.id.tv_custom_wrapper);

        btSearch = findViewById(R.id.bt_custom_search);
        etSearch = findViewById(R.id.et_custom_search);

        llImgs = findViewById(R.id.ll_imgs);

        tvAlpha.setOnClickListener(this);
        tvScale.setOnClickListener(this);
        tvTranslate.setOnClickListener(this);
        tvRotate.setOnClickListener(this);
        tvWrapper.setOnClickListener(this);
    }

    /**
     * 回退
     */
    public void back(View view){
        finish();
        overridePendingTransition(0, R.anim.custom_out);
    }

    /**
     * 回退
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.custom_out);
    }

}
