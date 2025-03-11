package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.Transformations;

import java.awt.*;
import java.util.HashSet;

public class RectCollider extends Component implements Updatable, Collider {

    private Rectangle rectangle;
    private Transform m_boundTransform;
    private Vector2Int size;
    private Vector2Int offset;
    private final HashSet<Collider> others = new HashSet<>();

    private RectCollider() {
    }

    public RectCollider(Transform boundTransform, Vector2Int size, Vector2Int offset) {
        this.size = size;
        this.offset = offset;
        rectangle = new Rectangle();
        rectangle.setSize(size.x, size.y);
        this.m_boundTransform = boundTransform;
    }

    public RectCollider(Transform boundTransform, Vector2Int size) {
        this.size = size;
        this.offset = new Vector2Int(0, 0);
        rectangle = new Rectangle();
        rectangle.setSize(size.x, size.y);
        this.m_boundTransform = boundTransform;
    }

    @Override
    public void update() {
        updateRectanglePosition();
    }

    public boolean isStatic() {
        return m_boundTransform.isStatic();
    }

    public boolean isOverlapping(Rectangle comparedRectangle) {
        return rectangle.intersects(comparedRectangle);
    }

    public boolean isOverlappingWithoutBorders(Rectangle comparedRectangle) {
        return rectangle.intersection(comparedRectangle).isEmpty();
    }

    private void updateRectanglePosition() {
        rectangle.setLocation(getCenterPosition().toPoint());
    }

    private Vector2Int getCenterPosition() {
        return Transformations.getCenterPositionWithOffsetSize(
                m_boundTransform.getPosition().getVector2Int(),
                offset,
                size
        );
    }

    public void resolveCollision(RectCollider other) {
        if (m_boundTransform.isStatic()) return;
        if (!isOverlapping(other.rectangle)) return;

        others.add(other);
        //for (Collider coll : others) System.out.println(this.getClass() + " collided with " + coll.getClass());

        // Calculate overlap distances on both axes
        int deltaX = (rectangle.x + rectangle.width / 2) - (other.rectangle.x + other.rectangle.width / 2);
        int deltaY = (rectangle.y + rectangle.height / 2) - (other.rectangle.y + other.rectangle.height / 2);
        int overlapX = (rectangle.width / 2 + other.rectangle.width / 2) - Math.abs(deltaX);
        int overlapY = (rectangle.height / 2 + other.rectangle.height / 2) - Math.abs(deltaY);

        if (overlapX > 0 && overlapY > 0) {
            // Resolve along the axis of least penetration
            if (overlapX < overlapY) {
                if (deltaX > 0) {
                    m_boundTransform.setX(m_boundTransform.getX() + overlapX);
                } else {
                    m_boundTransform.setX(m_boundTransform.getX() - overlapX);
                }
            } else {
                if (deltaY > 0) {
                    m_boundTransform.setY(m_boundTransform.getY() + overlapY);
                } else {
                    m_boundTransform.setY(m_boundTransform.getY() - overlapY);
                }
            }
        }
    }

    public void resolveCollision(Vector2Int circleCenter, float circleRadius) {
        // Calculate the distance between centers
        Vector2Int diff = new Vector2Int(
                (int) (circleCenter.x - getCenterPosition().x),
                (int) (circleCenter.y - getCenterPosition().y)
        );

        float distanceSquared = (float) (Math.pow(diff.x, 2) + Math.pow(diff.y, 2));

        // Calculate half-diagonal of the rectangle
        int halfDiagonal = (int) Math.sqrt((size.x / 2.0 * size.x / 2.0) +
                (size.y / 2.0 * size.y / 2.0));

        if (distanceSquared <= circleRadius * circleRadius + halfDiagonal) {
            // Resolve collision by adjusting the rectangle's position
            float overlap = (float) (circleRadius - Math.sqrt(distanceSquared));
            Vector2Int direction = new Vector2Int(diff.x, diff.y);
            direction.normalize();

            if (direction.x > 0) {
                m_boundTransform.setX(getCenterPosition().x + halfDiagonal);
            } else {
                m_boundTransform.setX(getCenterPosition().x - halfDiagonal);
            }

            if (direction.y > 0) {
                m_boundTransform.setY(getCenterPosition().y + halfDiagonal);
            } else {
                m_boundTransform.setY(getCenterPosition().y - halfDiagonal);
            }
        }
    }

    public void clearOthers(){
        others.clear();
    }

    public HashSet<Collider> getOthers(){
        return others;
    }

    public Entity getParentEntity(){
        return m_boundTransform.getParentEntity();
    }
}
