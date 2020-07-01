package xyz.doikki.customviewcollect.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by qiuyuan on 2020/7/1
 */

fun dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
}