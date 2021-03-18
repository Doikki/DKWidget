package xyz.doikki.widget.dragmsg;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

public class MessageBubbleView extends View {

    private PointF mFixedPoint, mDragPoint;
    private Paint mPaint, mTextPaint;
    private Path mPath;

    private int mRadius;

    private int mMinRadius;
    private float mFixedRadius;
    private ViewData mViewData;

    private int mUnreadMsgNum = 0;

    private boolean mIsFree;//是否可自由拖拽

    public MessageBubbleView(Context context) {
        this(context, null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPath = new Path();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(dp2px(14));
    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    @Override
    protected void onDraw(Canvas canvas) {

        String text = mUnreadMsgNum + "";

        //没有拖拽的时候
        if (mFixedPoint == null || mDragPoint == null) {
            mRadius = getWidth() / 2;
            mMinRadius = (int) (mRadius * 0.4);
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

            float x = getWidth() / 2 - mTextPaint.measureText(text) / 2;
            Paint.FontMetricsInt f = mTextPaint.getFontMetricsInt();
            float y = getHeight() / 2 + (f.bottom - f.top) / 2 - f.bottom;
            canvas.drawText(mUnreadMsgNum + "", x, y , mTextPaint);
            return;
        }

        //绘制拖拽圆
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mRadius, mPaint);

        float distance = (float) getDistance(mFixedPoint, mDragPoint);
        mFixedRadius = mRadius - distance / 16;

        if (mFixedRadius < mMinRadius || mIsFree) {
            mIsFree = true;
        } else {
            //绘制固定圆
            canvas.drawCircle(mFixedPoint.x, mFixedPoint.y, mFixedRadius, mPaint);

            mPath.reset();

            //求角a的值
            float deltaX = mDragPoint.x - mFixedPoint.x;
            float deltaY = mDragPoint.y - mFixedPoint.y;
            float a = (float) Math.atan(deltaY / deltaX);

            float startX = (float) (mFixedPoint.x + mFixedRadius * Math.sin(a));
            float startY = (float) (mFixedPoint.y - mFixedRadius * Math.cos(a));

            mPath.moveTo(startX, startY);

            float x1 = (float) (mDragPoint.x + mRadius * Math.sin(a));
            float y1 = (float) (mDragPoint.y - mRadius * Math.cos(a));
            //控制点取两个圆的中心点
            float controlX = mFixedPoint.x + deltaX / 2;
            float controlY = mFixedPoint.y + deltaY / 2;

            mPath.quadTo(controlX, controlY, x1, y1);

            float x2 = (float) (mDragPoint.x - mRadius * Math.sin(a));
            float y2 = (float) (mDragPoint.y + mRadius * Math.cos(a));

            mPath.lineTo(x2, y2);

            float endX = (float) (mFixedPoint.x - mFixedRadius * Math.sin(a));
            float endY = (float) (mFixedPoint.y + mFixedRadius * Math.cos(a));

            mPath.quadTo(controlX, controlY, endX, endY);
            mPath.close();

            //绘制连接两个圆的贝塞尔曲线
            canvas.drawPath(mPath, mPaint);
        }

        //绘制文字
        float x = mDragPoint.x - mTextPaint.measureText(text) / 2;
        Paint.FontMetricsInt f = mTextPaint.getFontMetricsInt();
        float y = mDragPoint.y + (f.bottom - f.top) / 2 - f.bottom;
        canvas.drawText(mUnreadMsgNum + "", x, y , mTextPaint);
    }

    /**
     * 获取两个点之间的距离
     */
    private double getDistance(PointF p1, PointF p2) {
        float deltaX = p1.x - p2.x;
        float deltaY = p1.y - p2.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                addToDecorView();
                break;
            case MotionEvent.ACTION_MOVE:
                updateDragPoint(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                removeFromDecorView();
                if (mFixedRadius >= mMinRadius && !mIsFree) {
                    reset();
                }
                break;
        }

        invalidate();
        return true;
    }

    private void removeFromDecorView() {
        ViewParent parent = this.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this);
        }
    }

    private void reset() {
        if (mViewData != null) {
            mViewData.mViewParent.addView(this, mViewData.mIndex, mViewData.mLayoutParams);
        }

        mDragPoint = null;
        mFixedPoint = null;
        mViewData = null;
    }

    private void addToDecorView() {
        Context context = getContext();

        if (context instanceof Activity) {
            FrameLayout decorView = (FrameLayout) ((Activity) context).getWindow().getDecorView();
            int[] points = new int[2];
            getLocationInWindow(points);
            int x = points[0] + getWidth() / 2;
            int y = points[1] + getHeight() / 2;
            //记录View在屏幕中的初始位置
            initPoint(x, y);

            ViewParent parent = this.getParent();
            if (parent instanceof ViewGroup) {
                //将其从原来的父容器中移除
                ((ViewGroup) parent).removeView(this);
                //记录原来的信息
                mViewData = new ViewData();
                mViewData.mIndex = ((ViewGroup) parent).indexOfChild(this);//所处的index
                mViewData.mViewParent = (ViewGroup) parent;//父容器
                mViewData.mLayoutParams = getLayoutParams();//LayoutParams
                //将其添加到decorView中
                decorView.addView(this, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
    }

    private void updateDragPoint(MotionEvent event) {
        mDragPoint.set(event.getRawX(), event.getRawY());
    }

    private void initPoint(float x, float y) {
        mFixedPoint = new PointF(x, y);
        mDragPoint = new PointF(x, y);
    }

    private class ViewData{
        ViewGroup mViewParent;
        int mIndex;
        ViewGroup.LayoutParams mLayoutParams;
    }

    public void setUnreadMsgNum(int num) {
        mUnreadMsgNum = num;
        invalidate();
    }
}
