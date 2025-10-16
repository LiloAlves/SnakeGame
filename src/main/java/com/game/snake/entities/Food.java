package com.game.snake.entities;

import java.awt.*;
import java.util.Random;

public class Food {
    private Point position;
    private final Random random = new Random();

    public Food(int width, int height, int blockSize) {
        generateNewPosition(width, height, blockSize);
    }

    public void generateNewPosition(int width, int height, int blockSize) {
        int x = random.nextInt(width / blockSize);
        int y = random.nextInt(height / blockSize);
        this.position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public void draw(Graphics g, int blockSize) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = position.x * blockSize;
        int y = position.y * blockSize;
        int size = blockSize - 2;

        GradientPaint redGradient = new GradientPaint(
                x, y, new Color(255, 80, 80),
                x + size, y + size, new Color(180, 0, 0));
        g2.setPaint(redGradient);
        g2.fillOval(x, y, size, size);

        g2.setColor(new Color(255, 255, 255, 100));
        g2.fillOval(x + size / 4, y + size / 6, size / 3, size / 3);

        g2.setColor(new Color(80, 40, 0));
        g2.fillRoundRect(x + size / 2 - 2, y - 4, 4, 6, 2, 2);

        g2.setColor(new Color(0, 180, 0));
        int[] leafX = { x + size / 2 + 1, x + size / 2 + 6, x + size / 2 + 2 };
        int[] leafY = { y - 3, y + 2, y + 4 };
        g2.fillPolygon(leafX, leafY, 3);

        g2.dispose();
    }
}
