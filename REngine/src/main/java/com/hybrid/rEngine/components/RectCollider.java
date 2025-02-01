package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.Transformations;
import java.awt.Rectangle;

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
        updateRectangleSize();
        this.m_boundTransform = boundTransform;
    }

    public RectCollider(Transform boundTransform) {
        this.size = boundTransform.getScale().getVector2Int();
        this.offset = new Vector2Int();
        rectangle = new Rectangle();
        updateRectangleSize();
        this.m_boundTransform = boundTransform;
    }
    
    public void setSize(Vector2Int size){
        this.size = size;
        updateRectangleSize();
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

    private void updateRectangleSize() {
        rectangle.setSize(size.x, size.y);
    }

    private Vector2Int getCenterPosition() {
        return Transformations.getCenterPositionWithOffsetSize(m_boundTransform.getPosition().getVector2Int(), offset, size);
        
//        Vector2Int halfSize = new Vector2Int(
//                Math.max(1, rectangle.width / 2),
//                Math.max(1, rectangle.height / 2));
//        
//        Vector2Int center = m_boundTransform.getRoundedPosition().subtract(halfSize);
//        center.add(offset);
//        
//        return center;
    }
}
