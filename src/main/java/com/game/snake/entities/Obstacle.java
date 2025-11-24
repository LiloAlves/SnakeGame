package com.game.snake.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Obstacle {
    private final Point position;

    private static final Map<Integer, TexturePaint> BRICK_PAINT_CACHE = new HashMap<>();

    public Obstacle(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void draw(Graphics g, int blockSize) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int positionX = position.x * blockSize;
        int positionY = position.y * blockSize;

        TexturePaint brickPaint = getOrCreateBrickPaint(blockSize);
        g2.setPaint(brickPaint);
        g2.fillRoundRect(positionX, positionY, blockSize, blockSize, 6, 6);

        g2.setPaint(null);
        g2.setColor(new Color(90, 90, 90, 180));
        g2.setStroke(new BasicStroke(Math.max(1f, blockSize / 24f)));
        g2.drawRoundRect(positionX, positionY, blockSize, blockSize, 6, 6);

        g2.setPaint(new GradientPaint(positionX, positionY, new Color(0, 0, 0, 40),
                positionX, positionY + blockSize, new Color(0, 0, 0, 0)));
        g2.fillRoundRect(positionX, positionY, blockSize, blockSize, 6, 6);

        g2.dispose();
    }

    private static TexturePaint getOrCreateBrickPaint(int blockSize) {
        TexturePaint paint = BRICK_PAINT_CACHE.get(blockSize);
        if (paint != null)
            return paint;

        int tileX = Math.max(13, blockSize);
        int tile = tileX;

        BufferedImage img = new BufferedImage(tile, tile, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color mortar = new Color(225, 220, 210);
        Color brick = new Color(178, 74, 50);
        Color brick2 = new Color(165, 66, 45);
        Color shadow = new Color(0, 0, 0, 35);
        Color highlight = new Color(255, 255, 255, 30);

        int grout = Math.max(1, blockSize / 12);

        g.setColor(mortar);
        g.fillRect(0, 0, tile, tile);

        int rows = 2;
        int cols = 3;

        int brickX = (tile - (cols + 1) * grout) / cols;
        int brickY = (tile - (rows + 1) * grout) / rows;

        g.setStroke(new BasicStroke(Math.max(1f, blockSize / 40f)));

        for (int r = 0; r < rows; r++) {
            int offset = (r % 2 == 1) ? (brickX / 2) : 0;

            for (int c = 0; c < cols; c++) {
                int x = grout + c * (brickX + grout) + offset;
                int y = grout + r * (brickY + grout);

                if (x + brickX > tile)
                    x -= (x + brickX - tile);

                g.setColor((c % 2 == 0) ? brick : brick2);
                g.fill(new Rectangle2D.Float(x, y, brickX, brickY));

                g.setColor(highlight);
                g.drawLine(x, y, x + brickX, y);
                g.drawLine(x, y, x, y + brickY);

                g.setColor(shadow);
                g.drawLine(x, y + brickY, x + brickX, y + brickY);
                g.drawLine(x + brickX, y, x + brickX, y + brickY);
            }
        }

        GradientPaint gp = new GradientPaint(0, 0, new Color(0, 0, 0, 20),
                0, tile, new Color(255, 255, 255, 10));
        g.setPaint(gp);
        g.fillRect(0, 0, tile, tile);

        g.dispose();

        paint = new TexturePaint(img, new Rectangle(0, 0, tile, tile));
        BRICK_PAINT_CACHE.put(blockSize, paint);
        return paint;
    }
}
