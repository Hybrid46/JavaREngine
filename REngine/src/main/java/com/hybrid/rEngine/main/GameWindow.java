package com.hybrid.rEngine.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {

    private final JFrame jframe;

    public GameWindow(GamePanel gamePanel, boolean fullScreenMode) {

        jframe = new JFrame();

        jframe.setAlwaysOnTop(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);

        if (fullScreenMode) {
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = graphics.getDefaultScreenDevice();
            device.setFullScreenWindow(jframe);
        }

        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
            }
        });

    }

}
