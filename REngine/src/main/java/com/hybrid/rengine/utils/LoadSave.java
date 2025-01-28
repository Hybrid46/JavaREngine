package com.hybrid.rEngine.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class LoadSave {

    public static BufferedImage GetSprite(String fileName) {
        fileName = "/" + fileName;
        System.out.println("[LoadSave] Loading sprite -> " + fileName);
        
        BufferedImage img = null;
        final ClassLoader clsldr = LoadSave.class.getClassLoader();
        InputStream inputStream = clsldr.getResourceAsStream(fileName);

        if (inputStream == null){
            System.err.println("[LoadSave] Failed to load sprite -> " + fileName);
            //return null;
        }
        
        try {
            img = ImageIO.read(inputStream);
            System.out.println("[LoadSave] Loaded sprite -> " + fileName);
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
