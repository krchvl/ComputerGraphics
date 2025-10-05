package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Arc {
    private final int cx, cy, radius;
    private final double startAngleDeg, sweepAngleDeg;
    private final Color startColor, endColor;
    private double thickness = 1.0;

    public Arc(int cx, int cy, int radius,
               double startAngleDeg, double sweepAngleDeg,
               Color startColor, Color endColor) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.startAngleDeg = startAngleDeg;
        this.sweepAngleDeg = sweepAngleDeg;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public Arc thickness(double px) {
        this.thickness = Math.max(0.1, px);
        return this;
    }

    public void draw(GraphicsContext gc) {
        ArcRasterization.drawArc(gc, cx, cy, radius, startAngleDeg, sweepAngleDeg, startColor, endColor, thickness);
    }
}