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
    private boolean moving = false, attacking = false, turning = false;
    private boolean left, up, right, down;
    private Turret turret;

    public Player(Game game, int owner) {
        super(game, owner);

        Transform transform = getTransform();

        Renderer renderer;
        if (owner == 0) {
            renderer = new Renderer(transform, "tank_green_body.png", 10);
        }
        else {
            renderer = new Renderer(transform, "tank_red_body.png", 10);
        }
        addComponent(renderer);

        RectCollider rectCollider = new RectCollider(transform, new Vector2Int(64, 64));
        addComponent(rectCollider);

        turret = new Turret(game, owner);
        Parent parent = new Parent(getTransform());
        parent.addChild(turret.getTransform());
        addComponent(parent);
    }

    public void update() {
        Transform transform = getTransform();

        Vector2 currentPos = transform.getPosition();
        Vector2 velocity = transform.getForward().multiply(updatePos().y);
        Vector2 positionAndVelocity = currentPos.add(velocity);

        transform.setPosition(positionAndVelocity);
        transform.addRotation(updatePos().x);

        if (super.getOwner() == 0) {
            up = (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_W));
            down = (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_S));
            left = (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_A));
            right = (getGame().keyboardInput.isKeyPressed(KeyEvent.VK_D));

            if (getGame().mouseInput.isButtonPressed(MouseEvent.BUTTON1)) {
                //System.out.println("Player, button 1 pressed");
                setAttacking(true);
            }

            if (getGame().mouseInput.isButtonPressed(MouseEvent.BUTTON2)) {
                //System.out.println("Player, button 2 pressed");
            }

            if (getGame().mouseInput.isButtonPressed(MouseEvent.BUTTON3)) {
                //System.out.println("Player, button 3 pressed");
            }
        }

        if (attacking) doAttack();
    }

    private Vector2 updatePos() {
        if (moving) moving = false;
        if (turning) turning = false;

        if (!left && !right && !up && !down) {
            return new Vector2();
        }

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerRotationSpeed;
            turning = true;
        } else if (right && !left) {
            xSpeed = playerRotationSpeed;
            turning = true;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;
            moving = true;
        } else if (down && !up) {
            ySpeed = playerSpeed;
            moving = true;
        }

        return new Vector2(xSpeed, ySpeed);
    }

    public void doAttack() {
        //System.out.println("Attack!");
        if (!turret.markedToDestroy() && turret != null) {
            new Bullet(turret);
        }
        attacking = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void Destroy() {
        super.Destroy();
        turret.Destroy();
    }
}
