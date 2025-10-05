package com.cgvsu.rasterization;

public final class Vec2 {
    public final double x;
    public final double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Находит скалярное произведение двух векторов для определения угла между векторами (по знаку)
     * dot > 0 - острый угол;
     * dot = 0 - перпендикулярные векторы;
     * dot < 0 - тупой угол
     * @param a первый вектор
     * @param b второй вектор
     * @return число, скалярное произведение
     */
    public static double dot(Vec2 a, Vec2 b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * Находит векторное произведение двух векторов для определения ориентации векторов (по знаку)
     * cross > 0 - b слева от a (против часовой);
     * cross < 0 - b справа от a (по часовой);
     * cross = 0 - векторы коллинеарны
     * @param a первый вектор
     * @param b второй вектор
     * @return число
     */
    public static double crossZ(Vec2 a, Vec2 b) {
        return a.x * b.y - a.y * b.x;
    }

    /**
     * Создаёт вектор из угла
     * @param rad угол в радианах
     * @return вектор
     */
    public static Vec2 fromAngleRad(double rad) {
        return new Vec2(Math.cos(rad), Math.sin(rad));
    }
}