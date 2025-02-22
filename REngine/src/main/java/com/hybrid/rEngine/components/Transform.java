package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.utils.Transformations;

public class Transform extends Component {

    private Vector2 position;
    private float rotation; // In degrees
    private Vector2 scale;

    public Transform() {
        this.position = Vector2.zero();
        this.rotation = 0;
        this.scale = Vector2.one();
    }

    public Transform(Vector2 position, float rotation, Vector2 scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void setActive() {
        //super.setActive(true);
        System.err.println("Transforms must always active!");
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void addPosition(Vector2 position) {
        this.position.add(position);
    }

    public void addPosition(float x, float y) {
        this.position.x += x;
        this.position.y += y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void addRotation(float rotation) {
        this.rotation += rotation;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y) {
        this.scale.x = x;
        this.scale.y = y;
    }

    public void addScale(Vector2 scale) {
        this.scale.add(scale);
    }

    public void addScale(float x, float y) {
        this.scale.x += x;
        this.scale.y += y;
    }

    public Vector2 getForward() {
        return Transformations.getDirection(rotation + 90);
    }

    public Vector2 getRight() {
        return Transformations.getDirection(rotation + 180);
    }

    @Override
    public String toString() {
        return "Transform{" + "position=" + position + ", rotation=" + rotation + ", scale=" + scale + '}';
    }
}
