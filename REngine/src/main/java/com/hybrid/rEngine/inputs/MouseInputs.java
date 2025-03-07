package com.hybrid.rEngine.inputs;

import com.hybrid.rEngine.components.Updatable;
import com.hybrid.rEngine.main.GamePanel;
import com.hybrid.rEngine.math.Vector2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MouseInputs implements MouseListener, MouseMotionListener, Updatable {

    private final GamePanel gamePanel;

    private final Set<Integer> pressedButtons = new HashSet<>();
    private final Set<Integer> releasedButtons = new HashSet<>();
    private final Set<Integer> heldButtons = new HashSet<>();

    private Vector2 mousePosition = new Vector2();
    private Vector2 mouseDelta = new Vector2();
    private Vector2 lastMousePosition = new Vector2();

    private boolean dragging = false;

    private final Map<Integer, Long> clickTimers = new HashMap<>();
    private final long doubleClickThreshold = 250; // ms

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void reset(){
        pressedButtons.clear();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if (heldButtons.add(button)) {
            pressedButtons.add(button);
        }
        dragging = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        heldButtons.remove(button);
        releasedButtons.add(button);
        dragging = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();
        long currentTime = System.currentTimeMillis();

        if (clickTimers.containsKey(button)) {
            long lastClickTime = clickTimers.get(button);
            if (currentTime - lastClickTime <= doubleClickThreshold) {
                handleDoubleClick(button, e.getX(), e.getY());
                clickTimers.remove(button);
            } else {
                handleSingleClick(button, e.getX(), e.getY());
                clickTimers.put(button, currentTime);
            }
        } else {
            handleSingleClick(button, e.getX(), e.getY());
            clickTimers.put(button, currentTime);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragging = true;
        updateMousePosition(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void update() {
        pressedButtons.clear();
        releasedButtons.clear();
        mouseDelta = new Vector2();
    }

    private void updateMousePosition(MouseEvent e) {
        Vector2 newMousePos = new Vector2(e.getX(), e.getY());
        mouseDelta = newMousePos.subtract(lastMousePosition);
        lastMousePosition = newMousePos;
        mousePosition = newMousePos;
    }

    private void handleSingleClick(int button, int x, int y) {
        System.out.println("Single Click at (" + x + ", " + y + ") with Button: " + button);
    }

    private void handleDoubleClick(int button, int x, int y) {
        System.out.println("Double Click at (" + x + ", " + y + ") with Button: " + button);
    }

    public boolean isButtonPressed(int button) {
        return pressedButtons.contains(button);
    }

    public boolean isButtonReleased(int button) {
        return releasedButtons.contains(button);
    }

    public boolean isButtonHeld(int button) {
        return heldButtons.contains(button);
    }

    public boolean isButtonClicked(int button) {
        return pressedButtons.contains(button) && releasedButtons.contains(button);
    }

    public Vector2 getMousePosition() {
        return mousePosition;
    }

    public Vector2 getMouseDelta() {
        return mouseDelta;
    }

    public boolean isDragging() {
        return dragging;
    }
}
