package com.hybrid.rEngine.main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.hybrid.rEngine.inputs.KeyboardInputs;
import com.hybrid.rEngine.inputs.MouseInputs;
import static com.hybrid.rEngine.main.Game.GAME_HEIGHT;
import static com.hybrid.rEngine.main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private Game game;
    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;

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
