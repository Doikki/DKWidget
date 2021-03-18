package xyz.doikki.widget.progress;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import xyz.doikki.widget.R;


/**
 * 圆环进度条
 * Created by Devlin_n on 2018/1/24.
 */

public class CircleProgressBar extends View {

    private Paint mPaint;
    private int mMaxProgress;
    private int mCurrentProgress;
    private int mColor;
    private int mProgressColor;
    private float mStrokeWidth;
    private int mTextColor;
    private float mTextSize;
    private RectF mRectF;
    private int mStyle;

    public static final int FILL = 0;
    public static final int STROKE = 1;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mColor = attributes.getColor(R.styleable.CircleProgressBar_ring_color, Color.BLACK);
        mProgressColor = attributes.getColor(R.styleable.CircleProgressBar_ring_progress_color, Color.WHITE);
        mStrokeWidth = attributes.getDimension(R.styleable.CircleProgressBar_ring_stroke_width, 5);
        mMaxProgress = attributes.getInt(R.styleable.CircleProgressBar_ring_max_progress, 100);

        mTextColor = attributes.getColor(R.styleable.CircleProgressBar_ring_text_color, Color.BLACK);
        mTextSize = attributes.getDimension(R.styleable.CircleProgressBar_ring_text_size, 14);
        mStyle = attributes.getInt(R.styleable.CircleProgressBar_ring_style, 0);

        attributes.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int w = getSize(widthMode, width);
        int h = getSize(heightMode, height);


        int minSize = Math.min(width, height);
        int widthOffset = (width - minSize) / 2;
        int heightOffset = (height - minSize) / 2;
        float halfStrokeWidth = mStrokeWidth / 2;
        mRectF.set(widthOffset + halfStrokeWidth,
                heightOffset + halfStrokeWidth,
                minSize + widthOffset - halfStrokeWidth,
                minSize + heightOffset - halfStrokeWidth);

        setMeasuredDimension(w, h);
    }


    private int getSize(int specMode, int specSize) {
        int result;
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            default:
                result = (int) (Resources.getSystem().getDisplayMetrics().density * 50);
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();//控件宽度
        int height = getHeight();//控件高度
        int centerX = width / 2;//中心点x坐标
        int centerY = height / 2;//中心点y坐标

        //画圆环
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        int minSize = Math.min(width, height);
        canvas.drawCircle(centerX, centerY, minSize / 2 - mStrokeWidth / 2, mPaint);


        int angle = (mCurrentProgress * 360) / mMaxProgress;

        switch (mStyle) {
            case FILL:
                //画进度
                mPaint.setColor(mProgressColor);
                mPaint.setStrokeWidth(mStrokeWidth);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawArc(mRectF, -90, angle, true, mPaint);
                break;
            case STROKE:
                //画文字
                mPaint.setColor(mTextColor);
                mPaint.setStrokeWidth(0);
                mPaint.setTextSize(mTextSize);
                int percent = mCurrentProgress * 100 / mMaxProgress;
                float textWidth = mPaint.measureText(percent + "%");
                int textHeight = (int) (mPaint.descent() - mPaint.ascent());
                canvas.drawText(percent + "%", centerX - textWidth / 2, centerY + textHeight / 2 - mPaint.descent(), mPaint);

                //画进度
                mPaint.setColor(mProgressColor);
                mPaint.setStrokeWidth(mStrokeWidth);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(mRectF, -90, angle, false, mPaint);
                break;
        }


    }


    public void setProgress(int progress) {
        this.mCurrentProgress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }
}