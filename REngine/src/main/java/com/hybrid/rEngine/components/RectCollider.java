package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.Transformations;

import java.awt.*;

public class RectCollider extends Component implements Updatable {

    private Rectangle rectangle;
    private Transform m_boundTransform;
    private Vector2Int size;
    private Vector2Int offset;

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
        if (isOverlapping(other.rectangle)) {
            // Calculate overlap
            int overlapWidth = (rectangle.width + other.rectangle.width - Math.abs(rectangle.x - other.rectangle.x));
            int overlapHeight = (rectangle.height + other.rectangle.height - Math.abs(rectangle.y - other.rectangle.y));

            // If there's overlap, adjust positions to resolve collision
            if (overlapWidth > 0 && overlapHeight > 0) {
                // Calculate the direction of movement
                Vector2Int thisVelocity = m_boundTransform.getVelocity().getVector2Int();
                Vector2Int otherVelocity = other.m_boundTransform.getVelocity().getVector2Int();

                // Stop overlapping objects
                if (thisVelocity.x * (rectangle.x - other.rectangle.x) + thisVelocity.y * (rectangle.y - other.rectangle.y) < 0) {
                    m_boundTransform.setX(rectangle.x - overlapWidth);
                } else {
                    m_boundTransform.setX(other.rectangle.x + other.rectangle.width);
                }

                if (thisVelocity.x * (rectangle.x - other.rectangle.x) + thisVelocity.y * (rectangle.y - other.rectangle.y) > 0) {
                    m_boundTransform.setY(other.rectangle.y + other.rectangle.height);
                } else {
                    m_boundTransform.setY(rectangle.y - overlapHeight);
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
}
