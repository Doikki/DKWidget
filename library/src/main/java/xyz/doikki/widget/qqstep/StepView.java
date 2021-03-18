package xyz.doikki.widget.qqstep;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import xyz.doikki.widget.R;


public class StepView extends View {

    private Paint mPaint1, mPaint2, mPaint3;

    private int mOuterColor;
    private int mInnerColor;
    private float mStrokeWidth;
    private int mStepTextColor;
    private float mStepTextSize;

    private int mCurrentStep = 0;
    private int mMaxStep = 1000;

    public static final int MAX_DEGREE = 270;

    private RectF mRectF;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepView);
        mOuterColor = typedArray.getColor(R.styleable.StepView_outerColor, Color.GREEN);
        mInnerColor = typedArray.getColor(R.styleable.StepView_innerColor, Color.RED);
        mStrokeWidth = typedArray.getDimension(R.styleable.StepView_strokeWidth, 6);
        mStepTextColor = typedArray.getColor(R.styleable.StepView_stepTextColor, Color.RED);
        mStepTextSize = typedArray.getDimension(R.styleable.StepView_stepTextSize, 20);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setColor(mInnerColor);
        mPaint1.setStrokeWidth(mStrokeWidth);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);//圆弧

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(mOuterColor);
        mPaint2.setStrokeWidth(mStrokeWidth);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeCap(Paint.Cap.ROUND);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setColor(mStepTextColor);
        mPaint3.setTextSize(mStepTextSize);
        //文字水平居中
        mPaint3.setTextAlign(Paint.Align.CENTER);

        mRectF = new RectF();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = getWidth();
        int halfSize = size / 2;
        float halfStroke = mStrokeWidth / 2;
        mRectF.set(halfStroke, halfStroke, size - halfStroke, size - halfStroke);
        canvas.drawArc(mRectF, 135, MAX_DEGREE, false, mPaint1);
        canvas.drawArc(mRectF, 135, MAX_DEGREE * mCurrentStep / mMaxStep, false, mPaint2);

        Paint.FontMetricsInt f = mPaint3.getFontMetricsInt();
        //文字垂直居中
        int dy = halfSize - (f.descent + f.ascent) / 2;
        canvas.drawText(mCurrentStep + "", halfSize, dy, mPaint3);
    }


    public void setStep(int step) {
        this.mCurrentStep = step;
        invalidate();
    }

    public void setMaxStep(int maxStep) {
        this.mMaxStep = maxStep;
    }
}
