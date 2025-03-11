package com.hybrid.tankGame;

import com.hybrid.rEngine.components.*;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.utils.Transformations;
import static com.hybrid.rEngine.math.MathUtils.lerp;

public class Turret extends Entity implements Updatable {

    private final float turnSpeed = 0.5f;

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

    //TODO -> extract methods
    private void turnToMouse() {
        Vector2 mousePosition = getGame().mouseInput.getMousePosition();
        Vector2 screenPosition = getGame().getCameraManager().getCamera().worldToScreen(getTransform().getPosition());
        Vector2 directionToMouse = mousePosition.subtract(screenPosition);
        directionToMouse.normalize();

        float targetAngle = Transformations.getAngleFromDirection(directionToMouse);
        float currentAngle = getTransform().getRotation();

        float angleDifference = targetAngle - currentAngle;
        // Normalize to [-180, 180]
        angleDifference = (angleDifference + 180) % 360 - 180;

        // Calculate IDW (Inverse Distance Weighting)
        // Add 1.0f to avoid division by zero
        float idw = 1.0f / (Math.abs(angleDifference) + 1.0f);

        float effectiveTurnSpeed = turnSpeed * idw;
        float newAngle = lerp(currentAngle, currentAngle + angleDifference, effectiveTurnSpeed);

        getTransform().setRotation(newAngle);
    }
}
