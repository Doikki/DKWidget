package com.dueeeke.customviewcollect.circlemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class CircleMenuLayout extends ViewGroup {


    private ListAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;

    private Paint mPaint;

    public CircleMenuLayout(Context context) {
        this(context, null);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);//ViewGroup默认不回调onDraw,调用此方法让其回调onDraw
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureSelf(widthMeasureSpec, heightMeasureSpec);
        measureChildViews();
    }

    private void measureChildViews() {
        int width = getMeasuredWidth();
        //宽度的1/4
        int childSize = (int) (width * (1 / 4f));
        int childMode = MeasureSpec.EXACTLY;

        int childCount = getChildCount();

        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            int measureSpec = MeasureSpec.makeMeasureSpec(childSize, childMode);
            child.measure(measureSpec, measureSpec);
        }
    }

    private void measureSelf(int widthMeasureSpec, int heightMeasureSpec) {

        int measuredWidth;
        int measuredHeight;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            measuredWidth = getSuggestedMinimumWidth();
            measuredWidth = measuredWidth == 0 ? getDefaultWidth() : measuredWidth;
            measuredHeight = getSuggestedMinimumHeight();
            measuredHeight = measuredHeight == 0 ? getDefaultHeight() : measuredHeight;
        } else {
            measuredWidth = width;
            measuredHeight = height;
        }
        //使宽高一致
        int size = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(size, size);
    }

    private int getDefaultHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    private int getDefaultWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left, top;
        float perAngle = (float) (Math.PI * 2 / childCount);
        int width = getMeasuredWidth();
        int itemWidth = (int) (width * (1 / 4f));
        int radius = width / 2;
        int dis = width / 2 - itemWidth / 2 - getPaddingLeft();

        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            left = (int) (radius + Math.cos(i * perAngle) * dis - itemWidth / 2);
            top = (int) (radius + Math.sin(i * perAngle) * dis - itemWidth / 2);
            child.layout(left, top, left + itemWidth, top + itemWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r = getMeasuredWidth() / 2;
        canvas.drawCircle(r, r, r, mPaint);
    }

    @Override
    protected void onAttachedToWindow() {
        if (mAdapter != null) {
            buildItems();
        }
        super.onAttachedToWindow();
    }

    private void buildItems() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final View view = mAdapter.getView(i, null, this);
            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(view, position);
                }
            });
            addView(view);
        }
    }


    public void setAdapter(ListAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
