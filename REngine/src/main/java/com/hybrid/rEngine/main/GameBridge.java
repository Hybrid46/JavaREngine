package com.hybrid.rEngine.main;

import com.hybrid.tankGame.LevelGenerator;
import com.hybrid.tankGame.Player;
import com.hybrid.tankGame.Turret;

//Special class to make a bridge between the Engine and Game
//Parent class for all Game variable and handles all the logic
public class GameBridge {

    private Game game;
    private Player player;
    private Turret playerTurret;

    private GameBridge() {
    }

    public GameBridge(Game game) {
        this.game = game;
    }

    public void startGame() {
        LevelGenerator levelGenerator = new LevelGenerator();
        levelGenerator.generateLevel(game);

        player = new Player(game);
        game.getCameraManager().setFollowEntity(player);

        System.out.println("Game started...");
    }

    public void updateGame() {
        //System.out.println("Game update");
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
