package xyz.doikki.widget.circleimage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircleImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private lateinit var rectF: RectF

    private val paint = Paint()

    /**
     * xfermode文档：https://developer.android.com/reference/android/graphics/PorterDuff.Mode
     * 先绘制的为dest，后绘制的为src
     */
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    private lateinit var maskBitmap: Bitmap

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())

        // 生成 mask bitmap
        maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(maskBitmap)
        canvas.drawOval(rectF, paint)
    }

    override fun onDraw(canvas: Canvas) {
        // 离屏渲染，防止背景干扰绘制内容
        val count = canvas.saveLayer(rectF, null, Canvas.ALL_SAVE_FLAG)
        super.onDraw(canvas)
        paint.xfermode = xfermode
        canvas.drawBitmap(maskBitmap, 0f, 0f, paint)
        canvas.restoreToCount(count)
    }
}