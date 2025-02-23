package com.hybrid.rEngine.main;

import com.hybrid.rEngine.components.Entity;
import com.hybrid.rEngine.components.RenderUpdatable;
import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.inputs.KeyboardInputs;
import com.hybrid.rEngine.inputs.MouseInputs;
import com.hybrid.rEngine.utils.ScreenUtils;
import com.hybrid.tankGame.LevelGenerator;
import com.hybrid.tankGame.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;

public class Game implements Runnable {

    public final KeyboardInputs keyboardInput;
    public final MouseInputs mouseInput;
    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private final int FPS_SET = ScreenUtils.getRefreshRate(0);
    private final int UPS_SET = 200;
    private final HashSet<Updatable> updatables = new HashSet<>();
    private final HashSet<RenderUpdatable> renderUpdatables = new HashSet<>();
    private final TreeMap<Integer, ArrayList<RenderUpdatable>> layeredRenderUpdatables = new TreeMap<>();
    private final HashSet<Entity> entities = new HashSet<>();
    private Thread gameThread;
    private Player player;

    public Game() {
        start();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel, false);
        gamePanel.requestFocus();

        keyboardInput = gamePanel.getKeyboardInputs();
        mouseInput = gamePanel.getMouseInputs();

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void start() {
        LevelGenerator levelGenerator = new LevelGenerator();
        levelGenerator.generateLevel(this);

        player = new Player(this);
    }

    private void update() {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
        keyboardInput.update();
        mouseInput.update();
    }

    public void render(Graphics2D g2d) {
        layeredRenderUpdatables.clear();

        for (RenderUpdatable renderUpdatable : renderUpdatables) {
            int renderUpdatableLayer = renderUpdatable.getLayer();

            if (!layeredRenderUpdatables.containsKey(renderUpdatableLayer)) {
                layeredRenderUpdatables.put(renderUpdatableLayer, new ArrayList<>());
            }

            layeredRenderUpdatables.get(renderUpdatableLayer).add(renderUpdatable);
        }

        for (int layer : layeredRenderUpdatables.keySet()) {
            for (RenderUpdatable renderer : layeredRenderUpdatables.get(layer)) {
                renderer.render(g2d);
            }
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

        long updateTime = 0;
        long renderTime = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                updateTime = System.nanoTime();
                update();
                updateTime = System.nanoTime() - updateTime;
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                renderTime = System.nanoTime();
                gamePanel.repaint();
                renderTime = System.nanoTime() - renderTime;
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames +
                        " | UPS: " + updates +
                        " | Entities: " + entities.size() +
                        " | Updatables: " + updatables.size() +
                        " | RenderUpdatables: " + renderUpdatables.size() +
                        " | update time: " + updateTime / 1000f + " micro sec" +
                        " | render time: " + renderTime / 1000f + " micro sec" +
                        " | Avg update time: " + (int) (updateTime / 1000f / 1000f * UPS_SET) + " ms" +
                        " | Avg render time: " + (int) (renderTime / 1000f / 1000f * FPS_SET) + " ms");
                frames = 0;
                updates = 0;
            }
        }
    }

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

    public void registerUpdatables(Collection<Updatable> updatable) {
        updatables.addAll(updatable);
    }

    public void unregisterUpdatables(Collection<Updatable> updatable) {
        updatables.removeAll(updatable);
    }

    public void registerRenderUpdatables(Collection<RenderUpdatable> updatable) {
        renderUpdatables.addAll(updatable);
    }

    public void unregisterRenderUpdatables(Collection<RenderUpdatable> updatable) {
        renderUpdatables.removeAll(updatable);
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public void registerEntity(Entity entity) {
        entities.add(entity);
    }

    public void unregisterEntity(Entity entity) {
        entities.remove(entity);
    }

    public Player getPlayer() {
        return player;
    }
}
