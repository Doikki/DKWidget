package xyz.doikki.widget.dashboard

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import xyz.doikki.widget.util.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * 仪表盘
 * Created by Doikki on 2020/7/1
 */
class DashBoard : View {

    private val angle = 120f
    private val startAngle = 90 + angle / 2
    private val sweepAngle = 360 - angle
    private val radius = 150f.dp
    private val pointerLength = 120f.dp
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f.dp
    }
    private lateinit var bounds: RectF

    private val mark = Path().apply {
        addRect(RectF(0f, 0f, 2f.dp, 10f.dp), Path.Direction.CW)
    }
    private lateinit var effect: PathDashPathEffect

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds =
            RectF(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius)
        val arc = Path()
        arc.addArc(bounds, startAngle, sweepAngle)
        val pathMeasure = PathMeasure(arc, false)
        effect = PathDashPathEffect(
            mark,
            (pathMeasure.length - 2f.dp) / 20,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            //画刻度
            paint.pathEffect = effect
            it.drawArc(bounds, startAngle, sweepAngle, false, paint)
            //画弧
            paint.pathEffect = null
            it.drawArc(bounds, startAngle, sweepAngle, false, paint)
            //画指针
            it.drawLine(
                width / 2f,
                height / 2f,
                (cos(getAngleFromMark(5)) * pointerLength + width / 2f).toFloat(),
                (sin(getAngleFromMark(5)) * pointerLength + height / 2f).toFloat(),
                paint
            )
        }
    }

    private fun getAngleFromMark(mark: Int): Double =
        Math.toRadians(startAngle + sweepAngle.toDouble() / 20 * mark)
}