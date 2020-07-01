package xyz.doikki.customviewcollect.trackcolor;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import xyz.doikki.customviewcollect.R;


public class ColorTrackTextView extends AppCompatTextView {

    private int mOriginalColor;
    private int mTrackColor;

    private Paint mOriginalPaint, mTrackPaint;

    private Rect mRect;

    private float mOffset = 0f;
    private boolean mIsForward = true;

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        mOriginalColor = typedArray.getColor(R.styleable.ColorTrackTextView_originalColor, getTextColors().getDefaultColor());
        mTrackColor = typedArray.getColor(R.styleable.ColorTrackTextView_trackColor, getTextColors().getDefaultColor());
        typedArray.recycle();

        init();
    }

    private void init() {
        mOriginalPaint = getPaint(mOriginalColor);
        mTrackPaint = getPaint(mTrackColor);
        mRect = new Rect();
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        int x = (int) (getWidth() / 2 - mOriginalPaint.measureText(text) / 2);
        Paint.FontMetricsInt f = mOriginalPaint.getFontMetricsInt();
        int baseline = getHeight() / 2 + (f.bottom - f.top) / 2 - f.bottom;

        if (mIsForward) {
            canvas.drawText(text, x, baseline, mOriginalPaint);

            mRect.set(0, 0, (int) (mOffset * getWidth()), getHeight());
            canvas.clipRect(mRect);

            canvas.drawText(text, x, baseline, mTrackPaint);
        } else {
            canvas.drawText(text, x, baseline, mTrackPaint);

            mRect.set(0, 0, (int) (mOffset * getWidth()), getHeight());
            canvas.clipRect(mRect);

            canvas.drawText(text, x, baseline, mOriginalPaint);
        }
    }

    public void startTrack(float offset, boolean isForward) {
        this.mOffset = offset;
        this.mIsForward = isForward;
        invalidate();
    }
}
