package com.hybrid.tankGame;

import com.hybrid.rEngine.components.*;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.utils.Transformations;

public class Turret extends Entity implements Updatable {

    public Turret(Game game) {
        super(game);

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
        //if (mousePosition.x == 0 && mousePosition.y == 0) return;
        Vector2 screenPosition = getGame().getCameraManager().getCamera().worldToScreen(getTransform().getPosition());
        Vector2 directionToMouse = mousePosition.subtract(screenPosition);
        directionToMouse.normalize();
        getTransform().setRotation(Transformations.getAngleFromDirection(directionToMouse) + 90);
    }
}
