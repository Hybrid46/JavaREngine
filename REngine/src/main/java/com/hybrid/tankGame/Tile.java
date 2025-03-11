package com.hybrid.tankGame;

import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.components.RectCollider;
import com.hybrid.rEngine.components.Renderer;
import com.hybrid.rEngine.components.Transform;
import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;

public class Tile extends Entity {
    public Tile(Game game, int x, int y, boolean walkable) {
        super(game);
        setStatic(true);

        Transform transform = getTransform();
        transform.setPosition(new Vector2(x, y));
        addComponent(transform);

        Renderer renderer = new Renderer(transform, walkable ? "concrete_road.png" : "dragon_tooth.png", 5);
        addComponent(renderer);

        if (!walkable) {
            RectCollider rectCollider = new RectCollider(getTransform(), new Vector2Int(64, 64));
            addComponent(rectCollider);
        }
    }
}
