package com.hybrid.rEngine.math;

import java.awt.*;

public class Vector2 {

    public float x;
    public float y;

    // Constructors
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Static factory methods
    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    public static Vector2 one() {
        return new Vector2(1, 1);
    }

    public static Vector2 up() {
        return new Vector2(0, 1);
    }

    public static Vector2 right() {
        return new Vector2(1, 0);
    }

    // Basic operations
    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2 subtract(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2 scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2 multiply(Vector2 other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public Vector2 divide(float scalar) {
        if (scalar != 0) {
            this.x /= scalar;
            this.y /= scalar;
        }
        return this;
    }

    public Vector2 oneDivide() {
        this.x = 1f / this.x;
        this.y = 1 / this.y;
        return this;
    }

    public Vector2Int getVector2Int() {
        return new Vector2Int((int) this.x, (int) this.y);
    }

    public Vector2 getOneDivide() {
        return new Vector2(1 / this.x, 1 / this.y);
    }

    public Vector2 getNegated() {
        return new Vector2(this.x * -1f, this.y * -1f);
    }

    // Length and normalization
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 normalize() {
        float length = length();
        if (length != 0) {
            this.x /= length;
            this.y /= length;
        }
        return this;
    }

    public Vector2 negate() {
        this.x *= -1f;
        this.y *= -1f;
        return this;
    }

    public float distanceTo(Vector2 other) {
        return (float) Math.sqrt((other.x - this.x) * (other.x - this.x) + (other.y - this.y) * (other.y - this.y));
    }

    // Dot product
    public float dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    // Convert vector to angle (in degrees)
    public float toAngle() {
        return (float) Math.toDegrees(Math.atan2(this.y, this.x));
    }

    // Convert angle (in degrees) to vector
    public static Vector2 angleToVector2(float angle) {
        float rad = (float) Math.toRadians(angle);
        return new Vector2((float) Math.cos(rad), (float) Math.sin(rad));
    }

    public Vector2 rotate(float degrees) {
        float radians = (float) Math.toRadians(degrees);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;

        this.x = newX;
        this.y = newY;
        return this;
    }

    // Get the angle (in degrees) between this vector and another vector
    public float angleTo(Vector2 other) {
        float dot = this.dot(other);
        float lengths = this.length() * other.length();

        if (lengths == 0) {
            return 0;
        }

        float cosine = dot / lengths;
        cosine = Math.max(-1f, Math.min(1f, cosine));

        return (float) Math.toDegrees(Math.acos(cosine));
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")";
    }

    public boolean equals(Vector2 other) {
        final float EPSILON = 1e-6f;
        return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
    }
}
