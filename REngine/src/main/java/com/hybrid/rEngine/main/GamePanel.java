package com.hybrid.rEngine.main;

import com.hybrid.rEngine.inputs.KeyboardInputs;
import com.hybrid.rEngine.inputs.MouseInputs;
import com.hybrid.rEngine.utils.ScreenUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;

public class GamePanel extends JPanel {

    private final Game game;
    private final MouseInputs mouseInputs;
    private final KeyboardInputs keyboardInputs;
    private VolatileImage buffer;

    public GamePanel(Game game) {
        super();
        this.game = game;
        this.setBackground(Color.BLACK);

        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);

        Dimension size = setPanelSize();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        buffer = gc.createCompatibleVolatileImage(size.width, size.height);

        //setDoubleBuffered(false);

        // Hardware acceleration
        System.setProperty("sun.java2d.opengl", "true");
        String propValue = System.getProperty("sun.java2d.opengl");
        System.out.println("Hardware acceleration -> " + propValue);

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        setFocusable(true);
        requestFocus();
    }

    private Dimension setPanelSize() {
        Rectangle screenRect = ScreenUtils.getScreenBounds(0);
        Dimension size = new Dimension(screenRect.width / 2, screenRect.height / 2);
        System.out.println("Width: " + screenRect.width / 2 + " Height: " + screenRect.height / 2);
        setPreferredSize(size);
        return size;
    }

    @Override
    public void paintComponent(Graphics g) {
        //long paintTime = System.currentTimeMillis();
        super.paintComponent(g);

        do {
            if (buffer.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
                buffer = getGraphicsConfiguration().createCompatibleVolatileImage(getWidth(), getHeight());
            }

            Graphics2D g2d = buffer.createGraphics();

            // Clear screen
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());

            // Render game
            game.render(g2d);

            g2d.dispose();

            // Draw the buffer onto the panel
            g.drawImage(buffer, 0, 0, null);

            Toolkit.getDefaultToolkit().sync();

        } while (buffer.contentsLost());

        //paintTime = System.currentTimeMillis() - paintTime;
        //System.out.println("Paint time -> " + paintTime + " ms");
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
