package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.LoadSave;
import com.hybrid.rEngine.utils.Transformations;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Renderer extends Component implements Updatable, RenderUpdatable {

    private BufferedImage sprite;
    private String spriteFileName;
    private Transform m_boundTransform;
    private Rectangle boundingBox;
    private Vector2Int offset;
    private Vector2Int size;

    private Renderer() {
    }

    public Renderer(Transform boundTransform, String spriteFileName, Vector2Int offset, Vector2Int size) {
        this.m_boundTransform = boundTransform;
        this.offset = offset;
        this.size = size;
        this.spriteFileName = spriteFileName;
        loadSprite();
        boundingBox = new Rectangle(size.y, size.y);
    }

    public Rectangle getBoundingbox() {
        return boundingBox;
    }

    public void drawBoundingbox(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    @Override
    public void update() {
        updateBoundingBoxPosition();
    }

    @Override
    public void render(Graphics g) {
        drawBoundingbox(g);
        drawSprite(g);
    }

    public Vector2Int getSize() {
        return size;
    }

    private void updateBoundingBoxPosition() {
        Vector2Int center = Transformations.getCenterPositionWithOffsetSize(m_boundTransform.getPosition().getVector2Int(), offset, size);
        boundingBox.setLocation(center.toPoint());
    }

    private void drawSprite(Graphics g) {
        g.drawImage(sprite, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, null);
    }

    private void loadSprite() {
        sprite = LoadSave.GetSprite(spriteFileName);
    }
}
