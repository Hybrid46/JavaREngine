package com.hybrid.rEngine.inputs;

import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyboardInputs implements KeyListener, Updatable {

    private final GamePanel gamePanel;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final Set<Integer> releasedKeys = new HashSet<>();
    private final Set<Integer> changedKeys = new HashSet<>();

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void reset(){
        pressedKeys.clear();
    }

    public void update() {
        // Called once per game tick to handle state transitions
        changedKeys.clear();
        releasedKeys.clear();

        for (Integer keyCode : pressedKeys) {
            changedKeys.add(keyCode);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.add(keyCode)) { // Ensures state changes only when key is first pressed
            changedKeys.add(keyCode);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.remove(keyCode)) { // Ensures state changes only when key is released
            releasedKeys.add(keyCode);
            changedKeys.add(keyCode);
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public boolean isKeyReleased(int keyCode) {
        return releasedKeys.contains(keyCode);
    }

    public boolean isKeyChanged(int keyCode) {
        return changedKeys.contains(keyCode);
    }
}
