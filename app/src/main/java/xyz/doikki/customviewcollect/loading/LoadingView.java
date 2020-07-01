package xyz.doikki.customviewcollect.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import xyz.doikki.customviewcollect.R;

public class LoadingView extends LinearLayout {

    private ShapeView mShapeView;
    private ObjectAnimator mDownAnim;
    private ObjectAnimator mUpAnim;
    private ObjectAnimator mRotationAnim;
    private ObjectAnimator mScaleAnim;
    private View mShadow;

    private static final int ANIM_DURATION = 500;


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.layout_loading_view, null);
        mShapeView = view.findViewById(R.id.shape);
        mShadow = view.findViewById(R.id.shadow);
        addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startDownAnim();
    }


    private void startDownAnim() {

        if (mDownAnim == null) {
            mDownAnim = ObjectAnimator.ofFloat(mShapeView, "translationY", 0, dp2px(100));
            mDownAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    startUpAnim();
                }
            });
        }


        mScaleAnim = ObjectAnimator.ofFloat(mShadow, "scaleX", 1f, 0.2f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIM_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(mDownAnim, mScaleAnim);

        animatorSet.start();
    }

    private void startUpAnim() {
        if (mUpAnim == null) {
            mUpAnim = ObjectAnimator.ofFloat(mShapeView, "translationY", dp2px(100), 0);
            mUpAnim.setDuration(ANIM_DURATION);
            mDownAnim.setInterpolator(new DecelerateInterpolator());
            mUpAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mShapeView.changeShape();
                    startDownAnim();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    startRotationAnim();
                }
            });
        }

        mScaleAnim = ObjectAnimator.ofFloat(mShadow, "scaleX", 0.2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIM_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(mUpAnim, mScaleAnim);

        animatorSet.start();

    }

    private void startRotationAnim() {

        int degree = 0;

        switch (mShapeView.getCurrentShape()) {
            case ShapeView.SHAPE_CIRCLE:
            case ShapeView.SHAPE_RECT:
                degree = 180;
                break;
            case ShapeView.SHAPE_TRIANGLE:
                degree = -120;
                break;
        }

        if (mRotationAnim == null) {
            mRotationAnim = ObjectAnimator.ofFloat(mShapeView, "rotation", 0, degree);
            mRotationAnim.setDuration(ANIM_DURATION);
            mRotationAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mShapeView.setRotation(0);
                }
            });
        }

        mRotationAnim.setFloatValues(0, degree);
        mRotationAnim.start();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}
