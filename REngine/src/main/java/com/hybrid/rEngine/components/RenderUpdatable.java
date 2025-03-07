package com.hybrid.rEngine.components;

import com.hybrid.rEngine.main.CameraManager;

import java.awt.*;

public interface RenderUpdatable {
    void render(Graphics2D g2d, CameraManager cameraManager);
    int getLayer();
}
