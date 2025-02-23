package com.hybrid.rEngine.components;

import java.awt.*;

public interface RenderUpdatable {
    void render(Graphics2D g2d);
    int getLayer();
}
