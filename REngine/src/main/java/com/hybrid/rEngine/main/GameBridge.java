package com.hybrid.rEngine.main;

import com.hybrid.rEngine.math.Vector2;
import com.hybrid.tankGame.LevelGenerator;
import com.hybrid.tankGame.Player;

import java.awt.event.KeyEvent;

//Special class to make a bridge between the Engine and Game
//Parent class for all Game variable and handles all the logic
public class GameBridge {

    private int difficulty = 3;
    private Game game;
    private Player player;
    private Player[] enemys = new Player[difficulty];
    private LevelGenerator levelGenerator;

    private GameBridge() {
    }

    public GameBridge(Game game) {
        this.game = game;
    }

    public void startGame() {
        levelGenerator = new LevelGenerator(game);
        int enemyCount = difficulty;
        Vector2[] positions = levelGenerator.generateLevel(enemyCount);
        difficulty = positions.length - 1; //Sync back from positions -> On level load it sets the difficulty

        player = new Player(game, 0);
        player.getTransform().setPosition(positions[0]);
        game.getCameraManager().setFollowEntity(player);

        for (int i = 0; i < difficulty; i++) {
            enemys[i] = new Player(game, 1);
            enemys[i].getTransform().setPosition(positions[i + 1]);
        }

        System.out.println("Game started...");
    }

    public void updateGame() {
        //savegame hotkey
        if (game.keyboardInput.isKeyPressed(KeyEvent.VK_CONTROL) && game.keyboardInput.isKeyPressed(KeyEvent.VK_S)) {

            Vector2[] playerPositions = new Vector2[]{player.getTransform().getPosition()};

            levelGenerator.saveGame(playerPositions);
            System.out.println("Game saved!");
        }

        //delete save game hotkey
        if (game.keyboardInput.isKeyPressed(KeyEvent.VK_CONTROL) && game.keyboardInput.isKeyPressed(KeyEvent.VK_D)) {
            levelGenerator.deleteSaveGame();
            System.out.println("Save file removed!");
        }

        //TODO Update enemy controller components
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
}
