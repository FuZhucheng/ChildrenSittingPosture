package com.android.administrator.childrensittingposture.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.android.administrator.childrensittingposture.R;

/**
 * Created by Administrator on 2016/7/12.
 */
public class DrawCircle extends View {


    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 画笔属性
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 速度
     */
    private int mSpeed;

    /**
     * 是否应该开始下一圈，调换颜色
     */
    private boolean isNext = false;

    //下面是前两个构造方法
    public DrawCircle(Context context) {
        this(context, null);
    }

    public DrawCircle(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    /**
     * 必要的初始化，获得一些自定义的值
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public DrawCircle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyle, 0);//将获取的属性转化为我们最先设好的属性
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);//默认绿色
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);//默认红色
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));//默认为20

                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = a.getInt(attr, 20);// 默认20
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        // 绘图线程
        new Thread() {
            public void run() {
                while (true) {
                    mProgress++;
                    //每到一圈归0，firstcolor和secondcolor调换
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (!isNext)
                            isNext = true;
                        else
                            isNext = false;
                    }
                    postInvalidate();//界面刷新，重新执行ondraw
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            ;
        }.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int center = getWidth() / 2;//获取中心点到左边的距离

        int radius = center - mCircleWidth / 2;//半径是指圆环带中点到中心距离

        mPaint.setStrokeWidth(mCircleWidth);//圆环宽度
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//空心
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);//分别传入左上右下距离左边和上边的距离（左右距离左边，上下距离上边）

        if (!isNext) {
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center, center, radius, mPaint);//画那个不动圆环
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);//-90使每次都在正上方开始，Arc指那段会动的弧形
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
    }
}

