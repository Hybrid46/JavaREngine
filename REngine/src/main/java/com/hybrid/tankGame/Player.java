package com.hybrid.tankGame;

import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.components.Renderer;
import com.hybrid.rEngine.components.Transform;
import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;

import java.awt.event.KeyEvent;

public class Player extends Entity implements Updatable {

    private final float playerSpeed = 1.0f;
    private final float playerRotationSpeed = 0.5f;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;

    public Player(Game game) {
        super(game);

        Transform transform = getTransform();
        transform.setPosition(new Vector2(100, 100));
        addComponent(transform);

        Renderer renderer = new Renderer(transform, "tank_green.png");
        addComponent(renderer);

        game.registerUpdatable(this);
        game.registerEntity(this);
    }

    public void update() {
        Transform transform = getTransform();
        transform.addPosition(transform.getForward().multiply(updatePos().y));
        transform.addRotation(updatePos().x);

        setUp(getGame().keyboardInput.isKeyPressed(KeyEvent.VK_W));
        setDown(getGame().keyboardInput.isKeyPressed(KeyEvent.VK_S));
        setLeft(getGame().keyboardInput.isKeyPressed(KeyEvent.VK_A));
        setRight(getGame().keyboardInput.isKeyPressed(KeyEvent.VK_D));
    }

    private Vector2 updatePos() {
        moving = false;
        if (!left && !right && !up && !down) {
            return new Vector2();
        }

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerRotationSpeed;
        } else if (right && !left) {
            xSpeed = playerRotationSpeed;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;
        } else if (down && !up) {
            ySpeed = playerSpeed;
        }

        return new Vector2(xSpeed, ySpeed);
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

}
