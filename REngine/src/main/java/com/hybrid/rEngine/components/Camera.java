package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Matrix3x3;
import com.hybrid.rEngine.math.Vector2;

public class Camera extends Component {

    private final Transform m_transform;

    public Camera(Transform transform) {
        m_transform = transform;
    }

    //TODO not working perfectly
    public void setZoom(float factor) {
        float zoom = Math.max(0.1f, factor);
        m_transform.setScale(zoom, zoom);
    }

    //TODO test
    public void setRotation(float rotation) {
        m_transform.setRotation(rotation);
    }

    public Matrix3x3 getViewMatrix() {
        Matrix3x3 view = new Matrix3x3();
        view.translate(m_transform.getPosition().getNegated());
        view.rotate(-m_transform.getRotation());
        view.scale(m_transform.getScale().getOneDivide());
        return view;
    }

    public Vector2 worldToScreen(Vector2 vector) {
        return getViewMatrix().transformPoint(vector);
    }

    public Vector2 screenToWorld(Vector2 vector) {
        Matrix3x3 inverse = getViewMatrix();
        return inverse.transformPoint(vector);
    }
}
