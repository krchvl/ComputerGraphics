package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public final class ArcRasterization {

    private ArcRasterization() {}

    public static void drawArc(
            GraphicsContext gc,
            int cx, int cy, int radius,
            double startAngleDeg, double sweepAngleDeg,
            Color startColor, Color endColor
    ) {
        drawArc(gc, cx, cy, radius, startAngleDeg, sweepAngleDeg, startColor, endColor, 1.0);
    }

    public static void drawArc(
            GraphicsContext gc,
            int cx, int cy, int radius,
            double startAngleDeg, double sweepAngleDeg,
            Color startColor, Color endColor,
            double thicknessPx
    ) {
        if (gc == null || radius <= 0 || thicknessPx <= 0) {
            return;
        }

        double startRad = Math.toRadians(startAngleDeg);
        double sweepRad = Math.toRadians(sweepAngleDeg);
        double arcLen = Math.abs(sweepRad); // длина дуги - расстояние между начальной и конечной точкой дуги
        if (arcLen < 1e-9) return;

        boolean ccw = sweepRad >= 0.0; // проверка направления: true -> против часовой стрелки

        // векторы границ дуги
        Vec2 vStart = Vec2.fromAngleRad(startRad);
        Vec2 vEnd = Vec2.fromAngleRad(startRad + sweepRad);

        boolean isFullCircle = Math.abs(sweepRad) >= 2 * Math.PI - 1e-9;

        PixelWriter pw = gc.getPixelWriter();

        int margin = (int)Math.ceil(thicknessPx);
        int minX = cx - radius - margin;
        int maxX = cx + radius + margin;
        int minY = cy - radius - margin;
        int maxY = cy + radius + margin;

        double halfThick = thicknessPx / 2.0;
        double twoPi = Math.PI * 2.0;

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                double dx = x - cx; // разница между Х центра и Х точки
                double dy = (cy - y); // разница между Y центра и Y точки

                double dist = Math.hypot(dx, dy);

                if (Math.abs(dist - radius) > halfThick) continue;

                Vec2 vP = new Vec2(dx, dy); // вектор от центра к точке
                if (vP.x == 0.0 && vP.y == 0.0) continue;

                double M = Vec2.crossZ(vStart, vP); // найдём положение vStart и vP относительно друг друга
                double N = Vec2.crossZ(vP, vEnd); // найдём положение vP и vEnd относительно друг друга

                boolean inside;
                if (isFullCircle) {
                    inside = true;
                } else {
                    inside = ccw ? (M >= 0 && N >= 0) : (M <= 0 && N <= 0);
                }

                if (!inside) continue;

                double angCCW = Math2D.angleCCWNormalized(vStart, vP); // находим угол против часовой между вектором начала и вектором в точке
                double t;
                if (ccw) {
                    t = angCCW / arcLen;
                } else {
                    double angCW = (twoPi - angCCW); // угол по часовой
                    if (angCW >= twoPi - 1e-12) angCW = 0.0;
                    t = angCW / arcLen;
                }

                t = Math2D.clamp(t, 0.0, 1.0);

                Color c = ColorUtils.lerp(startColor, endColor, t);
                pw.setColor(x, y, c);
            }
        }
    }
}