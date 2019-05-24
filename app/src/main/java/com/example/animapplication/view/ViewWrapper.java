package com.example.animapplication.view;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

/**
 * 无法使用属性动画或者属性动画不起作用的情况：
 * 1.该字段没有set和get方法
 * 2.该属性的set方法仅仅改变了对象的属性值，但是没有将这种改变用动画的形式表现出来
 * 解决方法：
 * 1.如果有权限的话，给这个字段添加get和set方法，比如在自定义View中。
 * 2.使用一个包装类来封装该字段对应的类，间接为该字段提供get和set方法。
 * 3.采用ValueAnimator，监听动画过程，自己实现属性的改变
 * 关于ValueAnimator，其本身不作用于任何对象，也就是说直接使用它没有任何动画效果。
 * 它可以对一个值做动画，然后我们可以监听其动画过程，在动画过程中修改我们的对象的属性值，这样也就相当于我们的对象做了动画。
 *
 * 这里为了改变ImageView的宽度，采用第二种方法。
 * View包装类
 * 提供了get和set方法
 *
 * Created by xwxwaa on 2019/5/21.
 */

public class ViewWrapper {

    private View view;

    public ViewWrapper(View view) {
        this.view = view;
    }

    public int getWidth(){
        return view.getLayoutParams().width;
    }

    public void setWidth(int width){
        view.getLayoutParams().width = width;
        view.requestLayout();
    }

}