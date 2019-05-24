package com.example.animapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by xwxwaa on 2019/5/22.
 */

public class CircleView  extends View {

    private int width ;
    private int height ;
    private int colors;
    private Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
        invalidate();
    }

    //无参
    public CircleView(Context context) {
        super(context);
        init();
    }

    //有参
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        colors = Color.RED;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 定义宽高尺寸
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        // 设置View宽高
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(colors);
        canvas.drawCircle(width / 2, height / 2, width / 2, p);
    }
}
