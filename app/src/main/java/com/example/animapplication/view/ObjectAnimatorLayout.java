package com.example.animapplication.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.animapplication.R;
import com.example.animapplication.view.ObjectAnimatorView;

/**
 * Created by xwxwaa on 2019/5/22.
 */

public class ObjectAnimatorLayout extends RelativeLayout {
    ObjectAnimatorView view;
    Button animateBt;

    public ObjectAnimatorLayout(Context context) {
        super(context);
    }

    public ObjectAnimatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObjectAnimatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = (ObjectAnimatorView) findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "progress", 0, 68);
                animator.setDuration(1000);
                animator.setInterpolator(new FastOutSlowInInterpolator());
                animator.start();
            }
        });
    }
}

