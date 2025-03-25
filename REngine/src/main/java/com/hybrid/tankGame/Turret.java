package com.hybrid.tankGame;

import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.components.Renderer;
import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;

public class Turret extends Entity implements Updatable {

    private final float turnSpeed = 0.5f;

    public Turret(Game game, int owner) {
        super(game, owner);

        Renderer renderer = new Renderer(super.getTransform(), owner == 0 ? "tank_green_turret.png" : "tank_red_turret.png", 15);
        addComponent(renderer);
    }

    @Override
    public void update() {
        turnToDirection();
    }

    private void turnToDirection() {
        Vector2 targetScreenPosition = super.getOwner() == 0 ? getGame().mouseInput.getMousePosition() : getGame().getWindowSize().copy().divide(2f);
        Vector2 screenPosition = getGame().getCameraManager().getCamera().worldToScreen(getTransform().getPosition());
        Vector2 direction = targetScreenPosition.subtract(screenPosition);

        getTransform().turnToDirection(direction.normlaized(), turnSpeed);
    }
}
