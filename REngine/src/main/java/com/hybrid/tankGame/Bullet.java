package com.hybrid.tankGame;

import com.hybrid.rEngine.components.*;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;

public class Bullet extends Entity implements Updatable {
    final Vector2 velocity;
    final float speed = 3.5f;

    public Bullet(Entity parent) {
        super(parent.getGame());
        Transform parentTransform = parent.getTransform();
        float parentRotation = parentTransform.getRotation() - 90;
        Vector2 parentDirection = Vector2.angleToVector(parentRotation);
        //preserving parent velocity
        // velocity = parentTransform.getVelocity().add(parentDirection.multiply(speed));
        velocity = parentDirection.multiply(speed);

        Transform transform = getTransform();
        float initialOffset = 32f; // avoid self collision
        transform.setPosition(parentTransform.getPosition().add(parentDirection.multiply(initialOffset)));

        Renderer renderer = new Renderer(transform, "bullet.png", 10);
        addComponent(renderer);

        RectCollider rectCollider = new RectCollider(transform, new Vector2Int(4, 4));
        addComponent(rectCollider);

        getGame().registerUpdatable(this);
    }

    @Override
    public void update() {
        getTransform().setPosition(getTransform().getPosition().add(velocity));

        RectCollider collider = this.getComponent(RectCollider.class);

        for (Collider other : collider.getOthers()) {
            if (other instanceof RectCollider) {
                Entity otherEntity = ((RectCollider) other).getParentEntity();
                //otherEntity.Destroy();
                //Destroy();
            }

            //other collider types...
        }
    }
}
