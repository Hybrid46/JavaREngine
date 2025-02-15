package com.hybrid.tankGame;

import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.components.Renderer;
import com.hybrid.rEngine.components.Transform;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;

public class Tile extends Entity {
    public Tile(Game game, int x, int y, boolean walkable) {
        super(game);

        Transform transform = getTransform();
        transform.setPosition(new Vector2(x, y));
        addComponent(transform);

        Renderer renderer = new Renderer(transform, walkable ? "concrete_road.png" : "dragon_tooth.png");
        addComponent(renderer);

        game.registerEntity(this);
    }
}
