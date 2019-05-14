package com.dueeeke.customviewcollect.like;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

// 贝塞尔估值器
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF point1, point2;

    public BezierEvaluator(PointF pointF1, PointF pointF2) {
        this.point1 = pointF1;
        this.point2 = pointF2;
    }

    @Override
    public PointF evaluate(float t, PointF point0, PointF point3) {
        // t百分比， 0~1
        PointF point = new PointF();
        point.x = point0.x * (1 - t) * (1 - t) * (1 - t) //
                + 3 * point1.x * t * (1 - t) * (1 - t)//
                + 3 * point2.x * t * t * (1 - t)//
                + point3.x * t * t * t;//
        
        point.y = point0.y * (1 - t) * (1 - t) * (1 - t) //
                + 3 * point1.y * t * (1 - t) * (1 - t)//
                + 3 * point2.y * t * t * (1 - t)//
                + point3.y * t * t * t;//
        // 套用上面的公式把点返回
        return point;
    }

}