package com.hybrid.rEngine.main;

import com.hybrid.rEngine.components.Camera;
import com.hybrid.rEngine.components.Transform;
import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.Transformations;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CameraManager extends Entity implements Updatable {

    private Camera camera;
    private float cameraSpeed = 4;
    private Rectangle boundingBox;
    private final Vector2Int drawRange = new Vector2Int(128,128);

    public CameraManager(Game game) {
        super(game);

        camera = new Camera(this.getTransform());
        boundingBox = new Rectangle(drawRange.x, drawRange.y);

        game.registerUpdatable(this);
        game.registerEntity(this);
    }

    @Override
    public void update() {
        Vector2 addPosition = new Vector2();
        if (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_UP)) addPosition.y -= cameraSpeed;
        if (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_DOWN)) addPosition.y += cameraSpeed;
        if (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_RIGHT)) addPosition.x += cameraSpeed;
        if (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_LEFT)) addPosition.x -= cameraSpeed;

        Transform transform = getTransform();
        transform.setPosition( transform.getPosition().add(addPosition));

        updateBoundingBoxPosition();
    }

    private void updateBoundingBoxPosition() {
        Vector2Int center = Transformations.getCenterPositionWithOffsetSize(getTransform().getPosition().getVector2Int(), new Vector2Int(), drawRange);
        boundingBox.setLocation(center.toPoint());
    }

    public void drawBoundingBox(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
