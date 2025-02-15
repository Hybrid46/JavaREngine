package com.hybrid.rEngine.main;

import com.hybrid.rEngine.inputs.KeyboardInputs;
import com.hybrid.rEngine.inputs.MouseInputs;
import com.hybrid.rEngine.utils.ScreenUtils;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final Game game;
    private final MouseInputs mouseInputs;
    private final KeyboardInputs keyboardInputs;

    public GamePanel(Game game) {
        this.game = game;
        this.setBackground(Color.BLACK);
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);
        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Rectangle screenRect = ScreenUtils.GetScreenBounds(0);
        Dimension size = new Dimension(screenRect.width / 2,screenRect.height / 2);
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
