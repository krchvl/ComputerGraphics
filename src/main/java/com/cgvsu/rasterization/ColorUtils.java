package com.cgvsu.rasterization;

import javafx.scene.paint.Color;

public final class ColorUtils {
    private ColorUtils() {}

    /**
     * Статический метод, который производит линейную интерполяцию двух цветов
     * @param a первый цвет
     * @param b второй цвет
     * @param t расстояние от начала дуги до текущей точки
     * @return цвет
     */

    public static Color lerp(Color a, Color b, double t) {
        t = Math2D.clamp(t, 0.0, 1.0);

        double r = a.getRed() * (1.0 - t) + b.getRed() * t;
        double g = a.getGreen() * (1.0 - t) + b.getGreen() * t;
        double bl = a.getBlue() * (1.0 - t) + b.getBlue() * t;
        double al = a.getOpacity() * (1.0 - t) + b.getOpacity() * t;

        return new Color(r, g, bl, al);
    }
}