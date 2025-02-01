package com.hybrid.rEngine.math;

import java.awt.*;

public class Vector2Int {
    public int x;
    public int y;

    // Constructors
    public Vector2Int() {
        this.x = 0;
        this.y = 0;
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

    // Basic operations
    public Vector2Int add(Vector2Int other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2Int subtract(Vector2Int other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2Int scale(int scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2Int multiply(Vector2Int other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public Vector2Int scale(float scalar) {
        this.x = Math.round(this.x * scalar);
        this.y = Math.round(this.y * scalar);
        return this;
    }

    public Vector2Int multiply(Vector2Int other, float scalar) {
        this.x = Math.round(this.x * scalar * other.x);
        this.y = Math.round(this.y * scalar * other.y);
        return this;
    }

    // Length and normalization
    public int length() {
        return (int) Math.sqrt(x * x + y * y);
    }

    public Vector2Int normalize() {
        int length = length();
        if (length != 0) {
            this.x /= length;
            this.y /= length;
        }
        return this;
    }

    public int distanceTo(Vector2Int other) {
        return (int) Math.sqrt((other.x - this.x) * (other.x - this.x) + (other.y - this.y) * (other.y - this.y));
    }

    // Dot product
    public int dot(Vector2Int other) {
        return this.x * other.x + this.y * other.y;
    }

    public Point toPoint() {
        return new Point(x, y);
    }

    public Dimension toDimension() {
        return new Dimension(x, y);
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Vector2Int vector2 = (Vector2Int) obj;
        return vector2.x == x && vector2.y == y;
    }
}
