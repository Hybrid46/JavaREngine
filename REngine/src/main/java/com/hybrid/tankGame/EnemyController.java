package com.hybrid.tankGame;

import com.hybrid.rEngine.components.*;
import com.hybrid.rEngine.main.GameBridge;

public class EnemyController extends Component implements Updatable {

    GameBridge gameBridge;

    private EnemyController() {}

    public EnemyController(GameBridge gameBridge){
        this.gameBridge = gameBridge;
    }

    @Override
    public void update() {
        movement();
        attack();
    }

    private void movement(){

    }

    private void attack(){

    }
}
