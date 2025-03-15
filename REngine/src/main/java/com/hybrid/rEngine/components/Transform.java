package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.utils.Transformations;

import static com.hybrid.rEngine.math.MathUtils.lerp;

public class Transform extends Component {

    private final Entity entity;
    private Vector2 position;
    private Vector2 lastPosition;
    private float rotation; // In degrees
    private Vector2 scale;
    private Vector2 velocity;

    public Transform(Entity entity) {
        this.position = Vector2.zero();
        this.rotation = 0;
        this.scale = Vector2.one();
        this.entity = entity;
    }

    public void setActive() {
        //super.setActive(true);
        System.err.println("Transforms must always active!");
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getX(){
        return getPosition().x;
    }

    public float getY(){
        return getPosition().y;
    }

    public void setPosition(Vector2 position) {
        lastPosition = new Vector2(this.position.x, this.position.y);
        this.position = position;
        velocity = new Vector2(position.x - lastPosition.x, position.y - lastPosition.y);
    }

    public void setX(float x) {
        setPosition(new Vector2(x, position.y));
    }

    public void setY(float y) {
        setPosition(new Vector2(position.x, y));
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

    public void turnToDirection(Vector2 normalizedDirection, float turnSpeed){
        float targetAngle = Transformations.getAngleFromDirection(normalizedDirection);
        float currentAngle = rotation;

        float angleDifference = targetAngle - currentAngle;
        // Normalize to [-180, 180]
        angleDifference = (angleDifference + 180) % 360 - 180;

        // Calculate IDW (Inverse Distance Weighting)
        // Add 1.0f to avoid division by zero
        float idw = 1.0f / (Math.abs(angleDifference) + 1.0f);

        float effectiveTurnSpeed = turnSpeed * idw;

        rotation = lerp(currentAngle, currentAngle + angleDifference, effectiveTurnSpeed);
    }

    public boolean isStatic() {
        return entity.isStatic();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public String toString() {
        return "Transform{" +
                "position=" + position +
                ", lastPosition=" + lastPosition +
                ", rotation=" + rotation +
                ", scale=" + scale +
                ", entity=" + entity +
                ", velocity=" + velocity +
                '}';
    }

    public Entity getParentEntity(){
        return entity;
    }
}
