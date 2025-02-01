package com.hybrid.rEngine.main;

import com.hybrid.rEngine.inputs.KeyboardInputs;
import com.hybrid.rEngine.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static com.hybrid.rEngine.main.Game.GAME_HEIGHT;
import static com.hybrid.rEngine.main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private final Game game;
    private final MouseInputs mouseInputs;
    private final KeyboardInputs keyboardInputs;

    public GamePanel(Game game) {
        this.game = game;
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);
        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }

    public KeyboardInputs getKeyboardInputs() {
        return keyboardInputs;
    }

    public MouseInputs getMouseInputs() {
        return mouseInputs;
    }
}
