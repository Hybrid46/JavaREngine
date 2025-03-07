package com.hybrid.tankGame;

import com.hybrid.rEngine.components.*;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends Entity implements Updatable {

    private final float playerSpeed = 1.0f;
    private final float playerRotationSpeed = 0.5f;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private Turret turret;

    public Player(Game game) {
        super(game);

        Transform transform = getTransform();
        transform.setPosition(new Vector2(100.0f, 100.0f));

        Renderer renderer = new Renderer(transform, "tank_green_body.png", 10);
        addComponent(renderer);

        RectCollider rectCollider = new RectCollider(transform, new Vector2Int(64, 64));
        addComponent(rectCollider);

        turret = new Turret(game);
        Parent parent =new Parent(getTransform());
        parent.addChild(turret.getTransform());
        addComponent(parent);
        game.registerUpdatable(parent);

        game.registerUpdatable(this);
        game.registerEntity(this);
    }

    public void update() {
        Transform transform = getTransform();

        Vector2 currentPos = transform.getPosition();
        Vector2 velocity = transform.getForward().multiply(updatePos().y);
        Vector2 positionAndVelocity = currentPos.add(velocity);

        transform.setPosition(positionAndVelocity);
        transform.addRotation(updatePos().x);

        setUp(getGame().keyboardInput.isKeyPressed(KeyEvent.VK_W));
        setDown(getGame().keyboardInput.isKeyPressed(KeyEvent.VK_S));
        setLeft(getGame().keyboardInput.isKeyPressed(KeyEvent.VK_A));
        setRight(getGame().keyboardInput.isKeyPressed(KeyEvent.VK_D));

        if (getGame().mouseInput.isButtonPressed(MouseEvent.BUTTON1)) {
            System.out.println("Player, button 1 pressed");
            setAttacking(true);
            doAttack();
        }

        if (getGame().mouseInput.isButtonPressed(MouseEvent.BUTTON2)) {
            System.out.println("Player, button 2 pressed");
        }

        if (getGame().mouseInput.isButtonPressed(MouseEvent.BUTTON3)) {
            System.out.println("Player, button 3 pressed");
        }
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

    public void doAttack() {
        System.out.println("Attack!");
        attacking = false;
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
