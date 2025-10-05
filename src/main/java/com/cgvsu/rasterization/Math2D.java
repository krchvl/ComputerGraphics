package com.cgvsu.rasterization;

public final class Math2D {
    private Math2D() {}

    /**
     * Функция используется для получения нормализованного (против часовой) угла
     * между векторами
     * @param a первый вектор
     * @param b второй вектор
     * @return угол в радианах
     */
    public static double angleCCWNormalized(Vec2 a, Vec2 b) {
        double cross = Vec2.crossZ(a, b);
        double dot = Vec2.dot(a, b);
        double ang = Math.atan2(cross, dot); // угол между осью Х и вектором (cross, dot)
        if (ang < 0) {
            ang += Math.PI * 2.0; // работаем ТОЛЬКО с положительными (для простоты)
        }
        return ang;
    }

    public static double clamp(double v, double min, double max) {
        return (v < min) ? min : Math.min(v, max);
    }
}