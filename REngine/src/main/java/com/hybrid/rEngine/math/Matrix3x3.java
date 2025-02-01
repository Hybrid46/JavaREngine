package com.hybrid.rEngine.math;

public class Matrix3x3 {
    float[][] m = new float[3][3];

    public Matrix3x3() {
        identity();
    }

    public void identity() {
        m = new float[][] {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
    }

    public void translate(Vector2 vector) {
        m[0][2] += vector.x;
        m[1][2] += vector.y;
    }

    public void scale(Vector2 vector) {
        m[0][0] *= vector.x;
        m[1][1] *= vector.y;
    }

    public void rotate(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        float[][] r = {
            {cos, -sin, 0},
            {sin, cos, 0},
            {0, 0, 1}
        };
        multiply(r);
    }

    public void multiply(float[][] other) {
        float[][] result = new float[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = m[i][0] * other[0][j] +
                               m[i][1] * other[1][j] +
                               m[i][2] * other[2][j];
            }
        }
        m = result;
    }

    public Vector2 transformPoint(Vector2 vector) {
        float newX = m[0][0] * vector.x + m[0][1] * vector.y + m[0][2];
        float newY = m[1][0] * vector.x + m[1][1] * vector.y + m[1][2];
        return new Vector2 (newX, newY);
    }
}