package com.dueeeke.customviewcollect.like;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dueeeke.customviewcollect.R;

import java.util.Random;

public class LikeView extends FrameLayout {

    private LayoutParams mLayoutParams;

    private int mImageSize;

    private Drawable mLikeDrawable;

    private Random mRandom = new Random();

    public LikeView(Context context) {
        this(context, null);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LikeView);
        mLikeDrawable = typedArray.getDrawable(R.styleable.LikeView_imageSrc);
        mImageSize = (int) typedArray.getDimension(R.styleable.LikeView_imageSize, 30);
        typedArray.recycle();

        mLayoutParams = new LayoutParams(mImageSize, mImageSize, Gravity.BOTTOM | Gravity.CENTER);
    }


    public void addDrawable() {
        int color = Color.argb(255, mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
        Drawable drawable = tintDrawable(mLikeDrawable, color);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(drawable);
        addView(imageView, mLayoutParams);
        AnimatorSet enter = getEnterAnim(imageView);
        ValueAnimator path = getBezierValueAnimator(imageView);

        AnimatorSet all = new AnimatorSet();
        all.playSequentially(enter, path);
        all.start();
    }


    private AnimatorSet getEnterAnim(View view) {
        AnimatorSet enter = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0, 1f);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1f);

        enter.playTogether(alpha, scaleX, scaleY);
        enter.setDuration(500);
        enter.setTarget(view);

        return enter;
    }


    public static Drawable tintDrawable(@NonNull Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    /**
     * 贝塞尔曲线动画(核心，不断的修改ImageView的坐标ponintF(x,y) )
     */
    private ValueAnimator getBezierValueAnimator(final ImageView loveIv) {
        // 曲线的两个顶点
        PointF pointF2 = getPointF(2);
        PointF pointF1 = getPointF(1);
        // 起点位置
        PointF pointF0 = new PointF((float) getWidth() / 2 - mImageSize / 2, getHeight() - mImageSize);
        // 结束的位置
        PointF pointF3 = new PointF(mRandom.nextInt(getWidth()) - mImageSize, 0);
        // 估值器Evaluator,来控制view的行驶路径（不断的修改point.x,point.y）
        BezierEvaluator evaluator = new BezierEvaluator(pointF1, pointF2);
        // 属性动画不仅仅改变View的属性，还可以改变自定义的属性
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointF0,
                pointF3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 不断改变ImageView的x,y的值
                PointF pointF = (PointF) animation.getAnimatedValue();
                loveIv.setX(pointF.x);
                loveIv.setY(pointF.y);
                loveIv.setAlpha(1 - animation.getAnimatedFraction());// 得到百分比
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewParent parent = loveIv.getParent();
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(loveIv);
                }
            }
        });
        animator.setTarget(loveIv);
        animator.setDuration(3000);
        return animator;
    }

    private PointF getPointF(int i) {
        return new PointF(mRandom.nextInt(getWidth()) - mImageSize, mRandom.nextInt(getHeight()) / 2 * i);
    }
}
