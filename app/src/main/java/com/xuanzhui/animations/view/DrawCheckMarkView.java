/**
 * DrawCheckMarkView.java
 * <p/>
 * Created by xuanzhui on 2015/10/15.
 * Copyright (c) 2015 xuanzhui. All rights reserved.
 */
package com.xuanzhui.animations.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xuanzhui.animations.R;
import com.xuanzhui.animations.common.DisplayUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DrawCheckMarkView extends View {
    //绘制圆弧的进度值
    private int progress = 0;

    //打勾的起点
    int checkStartX;

    //线1的x轴增量
    private int line1X = 0;

    //线1的y轴增量
    private int line1Y = 0;

    //线2的x轴增量
    private int line2X = 0;

    //线2的y轴增量
    private int line2Y = 0;

    int step=2;

    //线的宽度
    private int lineThick = 4;

    //获取圆心的x坐标
    int center;

    //圆弧半径
    int radius;

    //定义的圆弧的形状和大小的界限
    RectF rectF;

    Paint paint;

    //控件大小
    float totalWidth;

    boolean secLineInited = false;

    public DrawCheckMarkView(Context context) {
        super(context);
        //init();
    }

    public DrawCheckMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Pattern p = Pattern.compile("\\d*");

        Matcher m = p.matcher(attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width"));

        if (m.find()) {
            totalWidth = Float.valueOf(m.group());
        }

        totalWidth = DisplayUtils.dp2px(context, totalWidth);

        init();
    }

    public DrawCheckMarkView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //init();
    }

    void init() {
        paint = new Paint();

        //设置画笔颜色
        paint.setColor(getResources().getColor(R.color.arc_succ_color));

        //设置圆弧的宽度
        paint.setStrokeWidth(lineThick);

        //设置圆弧为空心
        paint.setStyle(Paint.Style.STROKE);

        //消除锯齿
        paint.setAntiAlias(true);

        //获取圆心的x坐标
        center = (int) (totalWidth / 2);

        //圆弧半径
        radius = (int) (totalWidth / 2) - lineThick;

        checkStartX = (int) (center - totalWidth / 5);

        rectF = new RectF(center - radius,
                center - radius,
                center + radius,
                center + radius);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.w("check mark", "drawing... # progress=" + progress);

        if (progress < 100)
            progress += step;

        //根据进度画圆弧
        canvas.drawArc(rectF, 235, -360 * progress / 100, false, paint);

        //先等圆弧画完，画对勾
        if (progress >= 100) {
            if (line1X < radius / 3) {
                line1X+=step;
                line1Y+=step;
            }

            //画第一根线
            canvas.drawLine(checkStartX, center, checkStartX + line1X, center + line1Y, paint);

            if (line1X >= radius / 3) {

                if (!secLineInited) {
                    line2X = line1X;
                    line2Y = line1Y;

                    secLineInited=true;
                }

                line2X+=step;
                line2Y-=step;

                //画第二根线
                canvas.drawLine(checkStartX + line1X - lineThick / 2,
                        center + line1Y, checkStartX + line2X, center + line2Y, paint);
            }
        }

        //每隔6毫秒界面刷新
        if (line2X <= radius)
            postInvalidateDelayed(6);
    }
}
