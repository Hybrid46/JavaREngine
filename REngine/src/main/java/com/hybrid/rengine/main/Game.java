package com.hybrid.rEngine.main;

import com.hybrid.rEngine.components.*;
import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.tankGame.Player;
import java.awt.Graphics;
import java.util.HashSet;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 300;
    private final int UPS_SET = 200;
    private Player player;
    //private LevelManager levelManager;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private final HashSet<Updatable> updatables = new HashSet<>();
    private final HashSet<RenderUpdatable> renderUpdatables = new HashSet<>();

    public Game() {
        start();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel, false);
        gamePanel.requestFocus();

        updatables.add(gamePanel.getKeyboardInputs());
        updatables.add(gamePanel.getMouseInputs());
        
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void start() {
        //levelManager = new LevelManager(this);
        player = new Player();
        Transform transform = player.getComponent(Transform.class);
        transform.setPosition(new Vector2(100,100));
        player.addComponent(transform);
        
        Renderer renderer = new Renderer(transform, "tank_green.png", new Vector2Int(0,0), new Vector2Int(64,64));
        player.addComponent(renderer);
        
        updatables.add(player);
        updatables.addAll(player.getUpdatables());
        renderUpdatables.addAll(player.getRenderUpdatables());
    }

    private void update() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

    public void render(Graphics g) {
        for (RenderUpdatable renderUpdatable : renderUpdatables) {
            renderUpdatable.render(g);
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    //Updatables
    public void registerUpdatable(Updatable updatable) {
        updatables.add(updatable);
    }

    public void unregisterUpdatable(Updatable updatable) {
        updatables.remove(updatable);
    }

    public void registerRenderUpdatable(RenderUpdatable updatable) {
        renderUpdatables.add(updatable);
    }

    public void unregisterRenderUpdatable(RenderUpdatable updatable) {
        renderUpdatables.remove(updatable);
    }
    //-------

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
