package com.hybrid.rEngine.components;

import com.hybrid.rEngine.math.Vector2Int;
import com.hybrid.rEngine.utils.LoadSave;
import com.hybrid.rEngine.utils.Transformations;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Renderer extends Component implements Updatable, RenderUpdatable {

    private BufferedImage image;
    private String spriteFileName;
    private Transform m_boundTransform;
    private Rectangle boundingBox;
    private Vector2Int offset;
    private Vector2Int size;

    private Renderer() {
    }

    public Renderer(Transform boundTransform, String spriteFileName) {
        this.m_boundTransform = boundTransform;
        this.spriteFileName = spriteFileName;
        loadImage();
        this.size = new Vector2Int(image.getWidth(), image.getHeight());
        this.offset = Vector2Int.zero();
        boundingBox = new Rectangle(size.x, size.y);
    }

    public Renderer(Transform boundTransform, String spriteFileName, Vector2Int offset, Vector2Int size) {
        this.m_boundTransform = boundTransform;
        this.spriteFileName = spriteFileName;
        loadImage();
        this.size = size;
        this.offset = offset;
        boundingBox = new Rectangle(size.x, size.y);
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

    private void updateBoundingBoxPosition() {
        Vector2Int center = Transformations.getCenterPositionWithOffsetSize(m_boundTransform.getPosition().getVector2Int(), offset, size);
        boundingBox.setLocation(center.toPoint());
    }

    private void loadImage() {
        image = LoadSave.GetSprite(spriteFileName);
    }

    private void drawImage(Graphics2D g2d) {
        double rotationRequired = Math.toRadians(m_boundTransform.getRotation());
        double locationX = boundingBox.width / 2f;
        double locationY = boundingBox.height / 2f;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2d.drawImage(op.filter(image, null), boundingBox.x, boundingBox.y, null);
    }
}
