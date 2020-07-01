package xyz.doikki.customviewcollect.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View {

    private Paint mTrianglePaint, mCirclePaint, mRectPaint;

    private Path mTriAnglePath;

    public static final int SHAPE_CIRCLE = 1;
    public static final int SHAPE_RECT = 2;
    public static final int SHAPE_TRIANGLE = 3;


    private int mCurrentShape = SHAPE_TRIANGLE;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTrianglePaint = getPaint(Color.RED);
        mCirclePaint = getPaint(Color.GREEN);
        mRectPaint = getPaint(Color.BLUE);

        mTriAnglePath = new Path();
    }


    public Paint getPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float size = getMeasuredWidth();

        switch (mCurrentShape) {
            case SHAPE_RECT:
                canvas.drawRect(0, 0, size, size, mRectPaint);
                break;
            case SHAPE_CIRCLE:
                canvas.drawCircle(size / 2, size / 2, size / 2, mCirclePaint);
                break;
            case SHAPE_TRIANGLE:
                mTriAnglePath.moveTo(size / 2, 0);
                mTriAnglePath.lineTo(0, (float) (size / 2 * Math.sqrt(3)));
                mTriAnglePath.lineTo(size, (float) (size / 2 * Math.sqrt(3)));
                mTriAnglePath.close();
                canvas.drawPath(mTriAnglePath, mTrianglePaint);
                break;
        }
    }

    public void changeShape() {
        mCurrentShape++;

        if (mCurrentShape == 4) {
            mCurrentShape = SHAPE_CIRCLE;
        }

        invalidate();
    }

    public int getCurrentShape() {
        return mCurrentShape;
    }
}
