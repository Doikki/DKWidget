package xyz.doikki.widget.pie

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import xyz.doikki.widget.util.dp2px
import kotlin.math.cos
import kotlin.math.sin

class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    private val angles = floatArrayOf(60f, 120f, 150f, 30f)
    private val colors = intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)

    private val radius = dp2px(150f)
    private val offset = dp2px(20f)
    private val offsetIndex = 1
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var rectF = RectF()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        angles.forEachIndexed { index, angle ->
            paint.color = colors[index]
            if (index == offsetIndex) {
                canvas.save()
                canvas.translate(
                    (offset * cos(Math.toRadians(startAngle + angle / 2.0))).toFloat(),
                    (offset * sin(Math.toRadians(startAngle + angle / 2.0))).toFloat()
                )
            }
            rectF.set(
                width / 2f - radius,
                height / 2f - radius,
                width / 2f + radius,
                height / 2f + radius,
            )
            canvas.drawArc(
                rectF,
                startAngle,
                angle,
                true,
                paint
            )
            if (index == offsetIndex) {
                canvas.restore()
            }
            startAngle += angle
        }
    }


}