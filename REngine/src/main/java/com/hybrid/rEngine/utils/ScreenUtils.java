package com.hybrid.rEngine.utils;

import java.awt.*;

public class ScreenUtils {
    public static Rectangle getMaximumScreenBounds() {
        int minx = 0, miny = 0, maxx = 0, maxy = 0;
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice device : environment.getScreenDevices()) {
            Rectangle bounds = device.getDefaultConfiguration().getBounds();
            minx = Math.min(minx, bounds.x);
            miny = Math.min(miny, bounds.y);
            maxx = Math.max(maxx, bounds.x + bounds.width);
            maxy = Math.max(maxy, bounds.y + bounds.height);
        }
        return new Rectangle(minx, miny, maxx - minx, maxy - miny);
    }

    public static Rectangle getScreenBounds(int displayIndex) {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = environment.getScreenDevices();
        if (displayIndex >= 0 && displayIndex < devices.length) {
            Rectangle screenRect = devices[displayIndex].getDefaultConfiguration().getBounds();
            return screenRect;
        }

        return new Rectangle(400, 300);
    }

    public static int getRefreshRate(int displayIndex) {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = environment.getScreenDevices();
        if (displayIndex >= 0 && displayIndex < devices.length) {
            return devices[displayIndex].getDefaultConfiguration().getDevice().getDisplayMode().getRefreshRate();
        }

        return 60;
    }
}
