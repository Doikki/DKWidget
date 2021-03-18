package xyz.doikki.widget.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private static final String TAG = "MyBehavior";
    private boolean mIsShow;

    public MyBehavior() {
    }

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.d(TAG, "onNestedScroll: dyConsumed : " + dyConsumed + "  dyUnconsumed: " + dyUnconsumed);

        if (dyConsumed > 0) {
            if (!mIsShow) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                child.animate().translationY(params.bottomMargin + child.getMeasuredHeight()).setDuration(400).start();
                mIsShow = true;
            }
        }

        if (dyConsumed < 0) {
            if (mIsShow) {
                child.animate().translationY(0).setDuration(400).start();
                mIsShow = !mIsShow;
            }
        }
    }
}
