package xyz.doikki.widget.alphabetindex;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import xyz.doikki.widget.R;


public class AlphabetIndexView extends View {

    private int mNormalColor;
    private int mSelectedColor;

    private Paint mNormalPaint, mSelectedPaint, mCenterPaint;
    private float mAlphabetSize;

    private String[] mAlphabet = {
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z",
    };
    private int mAH;
    private int mCurrentIndex = -1;

    public AlphabetIndexView(Context context) {
        this(context, null);
    }

    public AlphabetIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphabetIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphabetIndexView);
        mNormalColor = typedArray.getColor(R.styleable.AlphabetIndexView_normalColor, Color.BLACK);
        mSelectedColor = typedArray.getColor(R.styleable.AlphabetIndexView_selectedColor, Color.RED);
        mAlphabetSize = typedArray.getDimension(R.styleable.AlphabetIndexView_alphabetSize, 50);
        typedArray.recycle();
        init();

    }


    private void init() {
        mNormalPaint = getPaint(mNormalColor);
        mSelectedPaint = getPaint(mSelectedColor);
        mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterPaint.setTextSize(mAlphabetSize);
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(mAlphabetSize);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = getHeight();
        mAH = height / mAlphabet.length;

        for (int i = 0; i < mAlphabet.length; i++) {

            int x = (int) (getWidth() / 2 - mNormalPaint.measureText(mAlphabet[i]) / 2) + getWidth() / 2 - 50;

            Paint.FontMetricsInt f = mNormalPaint.getFontMetricsInt();

            int y = mAH / 2 + (f.bottom - f.top) / 2 - f.bottom;

            canvas.drawText(mAlphabet[i], x, y + mAH * i, mNormalPaint);

            if (i == mCurrentIndex) {
                //高亮
                canvas.drawText(mAlphabet[i], x, y + mAH * i, mSelectedPaint);
                //画圆
                mCenterPaint.setColor(Color.BLACK);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, 150, mCenterPaint);
                //写字
                mCenterPaint.setColor(Color.WHITE);
                int cx = (int) (getWidth() / 2 - mCenterPaint.measureText(mAlphabet[i]) / 2);
                Paint.FontMetricsInt cf = mCenterPaint.getFontMetricsInt();
                int cy = getHeight() / 2 + (cf.bottom - cf.top) / 2 - cf.bottom;
                canvas.drawText(mAlphabet[i], cx, cy, mCenterPaint);
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                if (event.getX() < getWidth() - 100) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                mCurrentIndex = (int) (y / mAH);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                mCurrentIndex = -1;
                invalidate();
                break;
        }

        return true;
    }
}
