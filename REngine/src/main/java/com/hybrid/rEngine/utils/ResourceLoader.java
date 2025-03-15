package com.hybrid.rEngine.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ResourceLoader {

    private static final HashMap<String, BufferedImage> images = new HashMap<>();

    public static BufferedImage GetSprite(String fileName) {
        //System.out.println("[LoadSave] Loading image -> " + fileName);

        if (images.containsKey(fileName)) {
            return images.get(fileName);
        }

        String resourcePath = "/images/" + fileName;
        InputStream inputStream = ResourceLoader.class.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            System.err.println("[LoadSave] Failed to load image -> " + resourcePath);
            return null;
        }

        try {
            BufferedImage img = ImageIO.read(inputStream);
            images.put(fileName, img);  // Use the original fileName as the key
            System.out.println("[LoadSave] Loaded image -> " + fileName);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}