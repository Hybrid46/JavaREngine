package com.hybrid.tankGame;

import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.components.Renderer;
import com.hybrid.rEngine.components.Transform;
import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;

public class Player extends Entity implements Updatable {

    private final Game game;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private final float playerSpeed = 1.0f;

    public Player(Game game) {
        super();
        this.game = game;

        Transform transform = getComponent(Transform.class);
        transform.setPosition(new Vector2(100, 100));
        addComponent(transform);

        Renderer renderer = new Renderer(transform, "tank_green.png", new Vector2Int(0, 0), new Vector2Int(64, 64));
        addComponent(renderer);

        game.registerUpdatable(this);
        game.registerUpdatables(getUpdatables());
        game.registerRenderUpdatables(getRenderUpdatables());
    }

    public void update() {
        Transform transform = getComponent(Transform.class);
        transform.addPosition(updatePos());
        transform.addRotation(updatePos().y);
    }

    private Vector2 updatePos() {
        moving = false;
        if (!left && !right && !up && !down) {
            return new Vector2();
        }

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left) {
            xSpeed = playerSpeed;
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
