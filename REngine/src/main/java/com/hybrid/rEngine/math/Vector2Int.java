package com.hybrid.rEngine.math;

import java.awt.*;

public class Vector2Int {
    public int x;
    public int y;

    // Constructors
    public Vector2Int() {
        this(0, 0);
    }

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Static factory methods
    public static Vector2Int zero() {
        return new Vector2Int(0, 0);
    }

    public static Vector2Int one() {
        return new Vector2Int(1, 1);
    }

    public static Vector2Int up() {
        return new Vector2Int(0, 1);
    }

    public static Vector2Int right() {
        return new Vector2Int(1, 0);
    }

    // Basic operations (return new instances)
    public Vector2Int add(Vector2Int other) {
        return new Vector2Int(x + other.x, y + other.y);
    }

    public Vector2Int subtract(Vector2Int other) {
        return new Vector2Int(x - other.x, y - other.y);
    }

    public Vector2Int multiply(Vector2Int other) {
        return new Vector2Int(x * other.x, y * other.y);
    }

    public Vector2Int multiply(int scalar) {
        return new Vector2Int(x * scalar, y * scalar);
    }

    public Vector2Int multiply(float scalar) {
        return new Vector2Int(Math.round(x * scalar), Math.round(y * scalar));
    }

    public Vector2Int divide(int scalar) {
        return new Vector2Int(x / scalar, y / scalar);
    }

    public Vector2 getOneDivide() {
        return new Vector2(1.0f / x, 1.0f / y);
    }

    public Vector2Int getNegated() {
        return new Vector2Int(-x, -y);
    }

    // Length and normalization
    public int length() {
        return (int) Math.sqrt(x * x + y * y);
    }

    public Vector2Int normalize() {
        int length = length();
        if (length != 0) {
            x /= length;
            y /= length;
        }
        return this;
    }

    public Vector2Int normlaized() {
        return new Vector2Int(x, y).normalize();
    }

    public int distanceTo(Vector2Int other) {
        return (int) Math.sqrt((other.x - x) * (other.x - x) + (other.y - y) * (other.y - y));
    }

    public Vector2 directionTo(Vector2Int other) {
        Vector2 diff = new Vector2(other.x - x, other.y - y);
        return diff.normalize();
    }

    // Dot product
    public int dot(Vector2Int other) {
        return x * other.x + y * other.y;
    }

    // Angle conversions
    public float toAngle() {
        return (float) Math.toDegrees(Math.atan2(y, x));
    }

    public static Vector2Int angleToVector2Int(float angle) {
        float rad = (float) Math.toRadians(angle);
        return new Vector2Int(
                Math.round((float) Math.cos(rad)),
                Math.round((float) Math.sin(rad))
        );
    }

    public Vector2Int rotate(float angle) {
        Vector2 rotated = new Vector2(x, y).rotate(angle);
        return new Vector2Int(Math.round(rotated.x), Math.round(rotated.y));
    }

    public float angleTo(Vector2Int other) {
        Vector2 thisVec = new Vector2(x, y);
        Vector2 otherVec = new Vector2(other.x, other.y);
        return thisVec.angleTo(otherVec);
    }

    // Conversions
    public Vector2 toVector2() {
        return new Vector2(x, y);
    }

    public Point toPoint() {
        return new Point(x, y);
    }

    public Dimension toDimension() {
        return new Dimension(x, y);
    }

    @Override
    public String toString() {
        return "Vector2Int(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2Int other = (Vector2Int) obj;
        return x == other.x && y == other.y;
    }
}