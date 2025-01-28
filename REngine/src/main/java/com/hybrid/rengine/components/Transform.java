package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;

public class Transform extends Component implements Updatable {

    private Vector2 position;
    private Vector2Int roundedPosition;
    private float rotation; // In degrees
    private Vector2Int scale;

    public Transform() {
        this.position = Vector2.zero();
        this.rotation = 0;
        this.scale = Vector2Int.one();
        roundedPosition = calcRoundedPosition();
    }

    public Transform(Vector2 position, float rotation, Vector2Int scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        roundedPosition = calcRoundedPosition();
    }

    @Override
    public void update() {
        roundedPosition = calcRoundedPosition();
    }

    public void setActive() {
        //super.setActive(true);
        System.err.println("Transforms must always active!");
    }

    //For rendering and collisions
    private Vector2Int calcRoundedPosition() {
        return new Vector2Int((int)(position.x), (int)(position.y));
    }

    public Vector2Int getRoundedPosition() {
        return roundedPosition;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector2Int getScale() {
        return scale;
    }

    public void setScale(Vector2Int scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Transform{" + "position=" + position + ", roundedPosition=" + roundedPosition + ", rotation=" + rotation + ", scale=" + scale + '}';
    }
}
