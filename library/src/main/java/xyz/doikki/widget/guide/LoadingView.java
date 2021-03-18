package xyz.doikki.widget.guide;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class LoadingView extends View {

    private Paint mPaint;

    private int[] mColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};
    private float mCurrentRotateAngle;
    private float mCurrentRadius = DEFAULT_RADIUS;
    private float mCurrentBgRadius;

    private static final int DEFAULT_RADIUS = 200;
    private PorterDuffXfermode mPorterDuffXfermode;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        float perAngle = (float) (Math.PI * 2 / mColors.length);
        for (int i = 0; i < mColors.length; i++) {

            double angle = i * perAngle + mCurrentRotateAngle;
            float cx = (float) (centerX + mCurrentRadius * Math.cos(angle));
            float cy = (float) (centerY + mCurrentRadius * Math.sin(angle));
            mPaint.setColor(mColors[i]);
            canvas.drawCircle(cx, cy, 20, mPaint);
        }


        mPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawCircle(centerX, centerY, mCurrentBgRadius, mPaint);
        mPaint.setXfermode(null);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
    }



    private void start() {
        ValueAnimator rotate = ValueAnimator.ofFloat(0, (float) (2 * Math.PI));
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRotateAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });


        ValueAnimator radius = ValueAnimator.ofFloat(DEFAULT_RADIUS, 0);
        radius.setInterpolator(new AnticipateInterpolator());
        radius.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        int h = getHeight();
        int w = getWidth();
        float r = (float) Math.sqrt(h * h + w * w);

        ValueAnimator circle = ValueAnimator.ofFloat(20, r);
        circle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentBgRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playSequentially(rotate, radius, circle);
        animatorSet.start();


    }
}
