package com.androidy.azsecuer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.androidy.azsecuer.R;

import java.util.Timer;
import java.util.TimerTask;

public class MyProgressBar extends View {

    private Paint paint;
    private int roundColor;
    private int roundProgressColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private int max;
    private int progress;
    private boolean textDisplayable;
    private int style;
    private int style1;
    public static final int STOKE = 0;
    public static final int FILL = 1;
    public MyProgressBar(Context context) {
        this(context,null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        // 属性的设置
        roundColor = array.getColor(R.styleable.MyProgressBar_roundColor,context.getResources().getColor(R.color.colorAccent));
        roundProgressColor = array.getColor(R.styleable.MyProgressBar_roundProgressColor,Color.GREEN);
        textSize = array.getDimension(R.styleable.MyProgressBar_textSize,15);
        textColor = array.getColor(R.styleable.MyProgressBar_textColor, Color.BLUE);
        roundWidth = array.getDimension(R.styleable.MyProgressBar_roundWidth,5);
        max = array.getInteger(R.styleable.MyProgressBar_max,100);
        progress=array.getInteger(R.styleable.MyProgressBar_progress,50);
        textDisplayable = array.getBoolean(R.styleable.MyProgressBar_textDisplayable,true);
        style = array.getInt(R.styleable.MyProgressBar_style,0);
        style1 = array.getInt(R.styleable.MyProgressBar_style1,0);
        array.recycle();// 释放一下
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆环
        int x =getWidth()/2;
        int r =(int)(getWidth()/2-roundWidth/2);
        paint.setColor(roundColor);
        paint.setStrokeWidth(roundWidth);
        switch (style1) {
            case STOKE:
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(x, x, r, paint);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle(x, x, r, paint);
                break;
        }
        //画Text
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT);
        String drawText = (int)(((float)progress / (float) max) * 100) +"%";
        float drawTextWidth = paint.measureText(drawText);// 画的文本信息的宽度
        if(textDisplayable && style == STOKE){// 是否需要画出文本信息
            canvas.drawText(drawText,x-drawTextWidth/2,x-textSize/4,paint);
        }
        //画圆弧
        paint.setColor(roundProgressColor);
        paint.setStrokeWidth(roundWidth);
        // 定义出圆弧的形状和边界
        RectF rectF  = new RectF(x-r,x-r,x+r,x+r);
        switch (style){
            case STOKE:
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(rectF,-90,((float) progress/(float) max)*360,false,paint);
            break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawArc(rectF,-90,((float) progress/(float) max)*360,true,paint);
                break;
        }
    }
    public synchronized void startSetProgress(final  int targetProgress){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                setProgress(getProgress() + 1);
                if(getProgress()>=targetProgress){
                    setProgress(targetProgress);
                    this.cancel();
                }
            }
        };
        timer.schedule(task,40,40);
    }
    public synchronized void startSetProgress1(final  int targetProgress){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
        int state = 0 ; // 动画状态 为0 代表减少 为1 代表增加、
            public void run() {
                switch (state){
                    case 0:
                        setProgress(getProgress()-1);
                        if(getProgress() <= 0)
                            state = 1;
                        break;
                    case 1:
                        setProgress(getProgress()+1);
                        if(getProgress() >= targetProgress)
                            this.cancel();
                        break;
                }
            }
        };
        timer.schedule(task,40,40);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getRoundProgressColor() {
        return roundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if(max < 0)
            this.setProgress(0);
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if(progress<0){
            this.progress=0;
        }
        if(progress>max){
            this.progress=max;
        }
        if (progress<=max){
            this.progress=progress;
            postInvalidate();// 回调OnDraw
        }
    }

    public boolean isTextDisplayable() {
        return textDisplayable;
    }

    public void setTextDisplayable(boolean textDisplayable) {
        this.textDisplayable = textDisplayable;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getStyle1() {
        return style1;
    }

    public void setStyle1(int style1) {
        this.style1 = style1;
    }
}
