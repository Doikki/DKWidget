package xyz.doikki.widget.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by doikki on 2020/7/1
 */

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = this.toFloat().dp
