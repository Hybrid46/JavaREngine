package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.LoadSave;
import com.hybrid.rEngine.utils.Transformations;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer extends Component implements Updatable, RenderUpdatable {

    private BufferedImage image;
    private String spriteFileName;
    private Transform m_boundTransform;
    private Rectangle boundingBox;
    private Vector2Int offset;
    private Vector2Int size;
    private int layer;

    private Renderer() {
    }

    public Renderer(Transform boundTransform, String spriteFileName, int layer) {
        this.m_boundTransform = boundTransform;
        this.spriteFileName = spriteFileName;
        loadImage();
        this.size = new Vector2Int(image.getWidth(), image.getHeight());
        this.offset = Vector2Int.zero();
        boundingBox = new Rectangle(size.x, size.y);
        this.layer = layer;
    }

    public Renderer(Transform boundTransform, String spriteFileName, Vector2Int offset, Vector2Int size, int layer) {
        this.m_boundTransform = boundTransform;
        this.spriteFileName = spriteFileName;
        loadImage();
        this.size = size;
        this.offset = offset;
        boundingBox = new Rectangle(size.x, size.y);
        this.layer = layer;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void drawBoundingBox(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    @Override
    public void update() {
        updateBoundingBoxPosition();
    }

    @Override
    public void render(Graphics2D g2d) {
        drawBoundingBox(g2d);
        drawImage(g2d);
    }

    public Vector2Int getSize() {
        return size;
    }

    public int getLayer() {
        return layer;
    }

    //returns true if layer changes
    public boolean setLayer(int newLayer) {
        if (layer != newLayer) {
            layer = newLayer;
            return true;
        }

        return false;
    }

    private void updateBoundingBoxPosition() {
        Vector2Int center = Transformations.getCenterPositionWithOffsetSize(m_boundTransform.getPosition().getVector2Int(), offset, size);
        boundingBox.setLocation(center.toPoint());
    }

    private void loadImage() {
        image = LoadSave.GetSprite(spriteFileName);
    }

    private void drawImage(Graphics2D g2d) {
        double centerX = boundingBox.width / 2f;
        double centerY = boundingBox.height / 2f;

        g2d.translate(boundingBox.x + centerX, boundingBox.y + centerY);
        g2d.rotate(Math.toRadians(m_boundTransform.getRotation()));
        g2d.drawImage(image, -boundingBox.width / 2, -boundingBox.height / 2, null);
        g2d.rotate(-Math.toRadians(m_boundTransform.getRotation()));
        g2d.translate(-(boundingBox.x + centerX), -(boundingBox.y + centerY));
    }
}
