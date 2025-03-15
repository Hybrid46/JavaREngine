package com.hybrid.rEngine.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {

    public static BufferedImage GetSprite(String fileName) {
        //System.out.println("[LoadSave] Loading image -> " + fileName);

        fileName = "/images/" + fileName;
        BufferedImage img = null;
        InputStream inputStream = ResourceLoader.class.getResourceAsStream(fileName);

        if (inputStream == null) {
            System.err.println("[LoadSave] Failed to load image -> " + fileName);
            //return null;
        }

        try {
            img = ImageIO.read(inputStream);
            //System.out.println("[LoadSave] Loaded image -> " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return img;
    }
}
