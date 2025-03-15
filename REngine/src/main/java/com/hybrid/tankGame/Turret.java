package com.hybrid.tankGame;

import com.hybrid.rEngine.components.*;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;

public class Turret extends Entity implements Updatable {

    private final float turnSpeed = 0.5f;

    public Turret(Game game, int owner) {
        super(game, owner);

        Renderer renderer = new Renderer(super.getTransform(), "tank_green_turret.png", 15);
        addComponent(renderer);

        game.registerUpdatable(this);
    }

    @Override
    public void update() {
        turnToMouse();
    }

    private void turnToMouse() {
        Vector2 mousePosition = getGame().mouseInput.getMousePosition();
        Vector2 screenPosition = getGame().getCameraManager().getCamera().worldToScreen(getTransform().getPosition());
        Vector2 directionToMouse = mousePosition.subtract(screenPosition);

        getTransform().turnToDirection(directionToMouse.normlaized(), turnSpeed);
    }
}
