package com.hybrid.rengine.components;

import com.hybrid.rEngine.components.Component;
import com.hybrid.rEngine.components.Transform;
import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Matrix3x3;

public class Camera extends Component implements Updatable {

    private final Transform m_transform;

    public Camera(Transform transform) {
        m_transform = transform;
    }

    public void setZoom(float factor) {
        float zoom = Math.max(0.1f, factor);
        m_transform.setScale(zoom, zoom);
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

    @Override
    public void update() {
        //bounds rect update
    }
}
