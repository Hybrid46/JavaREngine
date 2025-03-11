package com.hybrid.rEngine.math;

public class MathUtils {

    public static float lerp(float a, float b, float f) {
        return a * (1.0f - f) + (b * f);
    }

    //Remap?
}
