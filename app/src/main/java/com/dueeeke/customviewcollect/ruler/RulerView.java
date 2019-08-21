package com.dueeeke.customviewcollect.ruler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.OverScroller;

import java.math.BigDecimal;

public class RulerView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //顶部基准线的中点
    private Point o = new Point();

    private VelocityTracker mVelocityTracker;

    private OverScroller mScroller;

    //每个小刻度所占的像素
    private float mSmallScalePx;
    //每个大刻度所占的像素
    private float mLargeScalePx;
    //小刻度的总数量
    private int mTotalSmallScaleCount;
    //每个大刻度钟包含的小刻度数量
    private int mSmallScaleCountOfLargeScale;
    //量程（像素）
    private int mRange;

    //刻度颜色
    private int mScaleColor;
    //竖直基线颜色
    private int mBaseLineColor;
    //刻度值
    private int mScaleNumColor;
    //刻度值大小
    private int mScaleNumSize;


    //基准线归位
    private float mDeltaR;

    private int mLastX;

    private boolean isTouched;

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mVelocityTracker = VelocityTracker.obtain();

        mScroller = new OverScroller(getContext());

        mSmallScalePx = dp2px(10);

        mTotalSmallScaleCount = 1000;

        mSmallScaleCountOfLargeScale = 10;

        mRange = (int) (mTotalSmallScaleCount * mSmallScalePx);

        mLargeScalePx = mSmallScalePx * mSmallScaleCountOfLargeScale;

        mBaseLineColor = Color.GREEN;

        mScaleColor = Color.GRAY;

        mScaleNumColor = Color.BLACK;

        mScaleNumSize = dp2px(20);

        mPaint.setTextSize(mScaleNumSize);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        o.x = getWidth() / 2;
        o.y = 0;

        postResult();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //背景
        canvas.drawColor(Color.parseColor("#F5F8F5"));

        //顶部基线
        mPaint.setStrokeWidth(1);
        mPaint.setColor(mScaleColor);
        int w = getWidth();
        canvas.drawLine(0, o.y, mRange + w, o.y, mPaint);

        for (int i = 0; i <= mTotalSmallScaleCount; i++) {

            int startX = (int) (i * mSmallScalePx) + w / 2;

            //屏幕外的不绘制
            if (getScrollX() > startX || startX > getScrollX() + w) continue;

            int startY = o.y;
            int stopX = (int) (i * mSmallScalePx) + w / 2;

            int stopY;
            if (i % mSmallScaleCountOfLargeScale == 0) {
                stopY = o.y + 70;
                mPaint.setStrokeWidth(2);

                //大刻度文字
                String text = i / mSmallScaleCountOfLargeScale + "";
                float x = startX - mPaint.measureText(text) / 2;
                Paint.FontMetricsInt f = mPaint.getFontMetricsInt();
                float y = stopY + (f.descent - f.ascent) / 2f - f.descent + 40;
                mPaint.setColor(mScaleNumColor);
                canvas.drawText(text, x, y, mPaint);

            } else {
                stopY = o.y + 40;
                mPaint.setStrokeWidth(1);
            }

            //刻度线
            mPaint.setColor(mScaleColor);
            canvas.drawLine(startX, startY, stopX, stopY, mPaint);

            //竖直基准线
            mPaint.setStrokeWidth(6);
            mPaint.setColor(mBaseLineColor);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawLine(getScrollX() + o.x, o.y, getScrollX() + o.x, o.y + 80, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouched = true;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScrollX() > 0 && getScrollX() < mRange) {
                    int dx = x - mLastX;
                    scrollBy(-dx, 0);
                    postResult();
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouched = false;
                mVelocityTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) mVelocityTracker.getXVelocity();
                mScroller.fling(getScrollX(), 0, -xVelocity, 0, 0, mRange, 0, 0);
                invalidate();
                break;
        }
        mLastX = x;
        return true;
    }

    private void postResult() {

        float r = getScrollX() / mLargeScalePx;
        Log.d(TAG, "四舍五入之前: " + r);

        BigDecimal b = new BigDecimal(r);
        float finalR = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        Log.d(TAG, "四舍五入之后: " + r);

        mDeltaR = (finalR - r) * mLargeScalePx;
        Log.d(TAG, "差值: " + mDeltaR);

        if (mOnScaleChangeListener != null) {
            mOnScaleChangeListener.onScaleChange(finalR);
        }
    }


    private static final String TAG = "RulerView";

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postResult();
            invalidate();
        } else {
            if (!isTouched) {
                int dx = (int) mDeltaR;
                if (dx == 0) return;
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
            }
        }
    }


    private OnScaleChangeListener mOnScaleChangeListener;

    public interface OnScaleChangeListener {
        void onScaleChange(float scale);
    }

    public void setOnScaleChangeListener(OnScaleChangeListener onScaleChangeListener) {
        mOnScaleChangeListener = onScaleChangeListener;
    }

    private int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5);
    }
}
