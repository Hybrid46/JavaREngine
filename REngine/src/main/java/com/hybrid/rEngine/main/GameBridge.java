package com.hybrid.rEngine.main;

import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.tankGame.EnemyController;
import com.hybrid.tankGame.LevelGenerator;
import com.hybrid.tankGame.Player;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

//Special class to make a bridge between the Engine and Game
//Parent class for all Game variable and handles all the logic
public class GameBridge {

    private int difficulty = 5;
    private Game game;
    private Player player;
    private List<Player> enemys = new ArrayList<>();
    private LevelGenerator levelGenerator;

    private GameBridge() {
    }

    public GameBridge(Game game) {
        this.game = game;
    }

    public void startGame() {
        levelGenerator = new LevelGenerator(game);
        int enemyCount = difficulty;
        List<Vector2> positions = levelGenerator.generateLevel(enemyCount);
        difficulty = positions.size() - 1; //Sync back from positions -> On level load it sets the difficulty

        player = new Player(game, 0);
        player.getTransform().setPosition(positions.get(0));
        game.getCameraManager().setFollowEntity(player);

        for (int i = 1; i < difficulty + 1; i++) {
            Player newEnemy = new Player(game, i);
            enemys.add(newEnemy);
            newEnemy.getTransform().setPosition(positions.get(i));

            EnemyController enemy = new EnemyController(this, newEnemy);
            newEnemy.addComponent(enemy);
        }

        System.out.println("Game started...");
    }

    public void updateGame() {
        SaveGame();
        DeleteSaveGame();
        DestroyEntitys();
    }

    private void DestroyEntitys() {
        if (player != null && player.markedToDestroy()) {
            player = null;
            System.out.println("---Game over!---");
            //TODO game over
        }

        enemys.removeIf(Entity::markedToDestroy);
    }

    private void DeleteSaveGame() {
        if (game.keyboardInput.isKeyPressed(KeyEvent.VK_CONTROL) && game.keyboardInput.isKeyPressed(KeyEvent.VK_D)) {
            levelGenerator.deleteSaveGame();
            System.out.println("Save file removed!");
        }
    }

    private void SaveGame() {
        if (game.keyboardInput.isKeyPressed(KeyEvent.VK_CONTROL) && game.keyboardInput.isKeyPressed(KeyEvent.VK_S)) {

            List<Vector2> playerPositions = new ArrayList<>();
            playerPositions.add(player.getTransform().getPosition());
            for(Player enemy : enemys) {
                playerPositions.add(enemy.getTransform().getPosition());
            }

            levelGenerator.saveGame(playerPositions);
            System.out.println("Game saved!");
        }
    }

    public void windowFocusGained() {
        System.out.println("Window focus gained");
    }

    public void windowFocusLost() {
        //Keys can be stucked without reseting them!
        game.keyboardInput.reset();
        game.mouseInput.reset();

        System.out.println("Window focus lost");
    }

    public LevelGenerator getLevelGenerator() {
        return levelGenerator;
    }

    public Player getPlayer(int index) {
        if (index == 0) return player;
        if (index > 0 && index <= enemys.size()) return enemys.get(index -1);
        return null;
    }
}
