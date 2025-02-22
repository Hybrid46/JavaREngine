package com.hybrid.rEngine.main;

import com.hybrid.rEngine.inputs.KeyboardInputs;
import com.hybrid.rEngine.inputs.MouseInputs;
import com.hybrid.rEngine.utils.ScreenUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

    private final Game game;
    private final MouseInputs mouseInputs;
    private final KeyboardInputs keyboardInputs;
    private BufferedImage buffer;

    public GamePanel(Game game) {
        this.game = game;
        this.setBackground(Color.BLACK);

        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);
        Dimension size = setPanelSize();
        buffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        // Hardware acceleration
        System.setProperty("sun.java2d.opengl", "true");
        String propValue = System.getProperty("sun.java2d.opengl");
        System.out.println("Hardware acceleration -> " + propValue);

        setFocusable(true);
        requestFocus();
    }

    private Dimension setPanelSize() {
        Rectangle screenRect = ScreenUtils.getScreenBounds(0);
        Dimension size = new Dimension(screenRect.width / 2,screenRect.height / 2);
        System.out.println("Width: " + screenRect.width / 2 + " Height: " + screenRect.height / 2);
        setPreferredSize(size);
        return size;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = buffer.createGraphics();

        // Clear screen
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());

        game.render(g2d);

        g.drawImage(buffer, 0, 0, null);
        g2d.dispose();
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
