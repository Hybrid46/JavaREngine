package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animator extends Component implements Updatable, RenderUpdatable {

    private BufferedImage[] animation;
    private Vector2Int subImageSize;
    private Transform m_boundTransform;
    private Renderer m_renderer;
    private String atlasFileName;
    private int subImageCount;
    private int aniTick = 0;
    private int aniIndex = 0;
    private final int aniSpeed = 30; //UPS time
    private int layer;

    //TODO
    private Animator() {
    }

    public void Animator(Transform boundTransform, Renderer renderer, String atlasFileName, int subImageCount) {
        m_boundTransform = boundTransform;
        m_renderer = renderer;
        this.subImageCount = subImageCount;
        this.atlasFileName = atlasFileName;
        this.subImageSize = renderer.getSize();

        BufferedImage img = LoadSave.GetSprite(this.atlasFileName);

        animation = new BufferedImage[subImageCount];
        for (int i = 0; i < animation.length; i++) {
            animation[i] = img.getSubimage(i * subImageSize.x, 0, subImageSize.x, subImageSize.y);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2d) {
        updateAnimation();
        drawAnimation(g2d);
    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;

            if (aniIndex >= subImageCount) {
                aniIndex = 0;
            }
        }
    }

    private void drawAnimation(Graphics2D g2d) {
        //g2d.drawImage(animation[aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), subImageSize.x, subImageSize.y, null);
    }

    private void resetAnimation() {
        aniTick = 0;
        aniIndex = 0;
    }

    public int getLayer() {
        return layer;
    }
}
