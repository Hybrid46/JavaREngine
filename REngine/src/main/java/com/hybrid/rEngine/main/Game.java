package com.hybrid.rEngine.main;

import com.hybrid.rEngine.components.*;
import com.hybrid.rEngine.inputs.KeyboardInputs;
import com.hybrid.rEngine.inputs.MouseInputs;
import com.hybrid.rEngine.utils.ScreenUtils;

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
    private final ArrayList<Collider> colliders = new ArrayList<>();
    private final HashSet<Entity> entities = new HashSet<>();
    private Thread gameThread;
    private CameraManager cameraManager;
    private GameBridge gameBridge;

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
        gameBridge = new GameBridge(this);
        gameBridge.startGame();
        cameraManager = new CameraManager(this);
    }

    private void update() {
        colliders.clear();


        for (Updatable updatable : updatables) {
            updatable.update();
            if (updatable instanceof Collider) colliders.add((Collider)updatable);
        }
        keyboardInput.update();
        mouseInput.update();

        updateCollisions();

        gameBridge.updateGame();
    }

    private void updateCollisions(){
        for (int c = 0; c < colliders.size(); c++) {
            for (int o = 0; o < colliders.size(); o++) {
                if (c == o) continue;

                //rec-rect collision check
                if (colliders.get(c) instanceof RectCollider && colliders.get(o) instanceof RectCollider)  {
                    ((RectCollider) colliders.get(c)).resolveCollision((RectCollider) colliders.get(o));
                }

                //other collision types...
            }
        }
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
                renderer.render(g2d, cameraManager);
            }
        }

        cameraManager.drawBoundingBox(g2d);
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
        gameBridge.windowFocusLost();
    }

    public void windowFocusGained() {
        gameBridge.windowFocusGained();
    }

    public void registerEntity(Entity entity) {
        entities.add(entity);
    }

    public void unregisterEntity(Entity entity) {
        entities.remove(entity);
    }

    public CameraManager getCameraManager(){
        return cameraManager;
    }
}
