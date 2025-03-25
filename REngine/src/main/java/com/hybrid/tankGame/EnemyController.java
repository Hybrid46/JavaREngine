package com.hybrid.tankGame;

import com.hybrid.rEngine.components.Component;
import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.main.GameBridge;
import com.hybrid.rEngine.math.Vector2Int;

public class EnemyController extends Component implements Updatable {

    Player controlledPlayer;
    GameBridge gameBridge;
    private LevelGenerator levelGenerator;

    private EnemyController() {
    }

    public EnemyController(GameBridge gameBridge, Player parent) {
        this.gameBridge = gameBridge;
        this.levelGenerator = gameBridge.getLevelGenerator();
        this.controlledPlayer = parent;
    }

    @Override
    public void update() {
        movement();
        attack();
    }

    private void movement() {
        Vector2Int currentTilePosition = levelGenerator.worldPositionToTilePosition(controlledPlayer.getTransform().getPosition().copy());
        Vector2Int targetPosition = levelGenerator.getRandomTile();
        //TODO movement
    }

    private void attack() {
        if (Math.random() < Math.random() * 0.05f) controlledPlayer.setAttacking(true);
    }
}
