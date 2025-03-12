package com.hybrid.rEngine.main;

import com.hybrid.rEngine.components.Camera;
import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.Transformations;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CameraManager extends Entity implements Updatable {

    private final Vector2Int drawRange = new Vector2Int(512, 512);
    Vector2 screenCenter;
    private Camera camera;
    private float cameraSpeed = 2;
    private Rectangle boundingBox;
    private Entity followEntity;

    public CameraManager(Game game) {
        super(game);

        camera = new Camera(this.getTransform());
        Vector2 windowSize = game.getWindowSize();
        screenCenter = windowSize.divide(2f);
        screenCenter = screenCenter.round();
        getTransform().setPosition(screenCenter);
        boundingBox = new Rectangle(drawRange.x, drawRange.y);

        game.registerUpdatable(this);
    }

    @Override
    public void update() {
        //followEntity = null;
        if (followEntity != null) {
            Vector2 followPosition = followEntity.getTransform().getPosition().subtract(screenCenter);
            getTransform().setPosition(followPosition);
        } else {
            Vector2 addPosition = new Vector2();
            if (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_UP)) addPosition.y -= cameraSpeed;
            if (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_DOWN)) addPosition.y += cameraSpeed;
            if (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_RIGHT)) addPosition.x += cameraSpeed;
            if (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_LEFT)) addPosition.x -= cameraSpeed;

            getTransform().setPosition(getTransform().getPosition().add(addPosition));
        }

        updateBoundingBoxPosition();
    }

    private void updateBoundingBoxPosition() {
        Vector2Int center = Transformations.getCenterPositionWithOffsetSize(this.getTransform().getPosition().getVector2Int(), new Vector2Int(), drawRange);
        boundingBox.setLocation(camera.screenToWorld(center.toVector2()).add(screenCenter).toPoint());
    }

    public void drawBoundingBox(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setFollowEntity(Entity entity) {
        followEntity = entity;
    }

    public Entity getFollowEntity(){
        return followEntity;
    }

    public Rectangle getBoundingBox(){
        return boundingBox;
    }
}
