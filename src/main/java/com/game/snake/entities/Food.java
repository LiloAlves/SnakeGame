package com.game.snake.entities;

import java.awt.*;
import java.util.Random;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Food {

    private Point position;

    @Getter(AccessLevel.NONE)
    private final Random random = new Random();

    public Food(int width, int height, int blockSize) {
        generateNewPosition(width, height, blockSize);
    }

    public void generateNewPosition(int width, int height, int blockSize) {
        int positionX = random.nextInt(width / blockSize);
        int positionY = random.nextInt(height / blockSize);
        this.position = new Point(positionX, positionY);
    }

    public void draw(Graphics g, int blockSize) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int positionX = position.x * blockSize;
        int positionY = position.y * blockSize;

        int size = blockSize - 2;

        GradientPaint redGradient = new GradientPaint(
                positionX, positionY, new Color(255, 80, 80),
                positionX + size, positionY + size, new Color(180, 0, 0));
        g2.setPaint(redGradient);
        g2.fillOval(positionX, positionY, size, size);

        g2.setColor(new Color(255, 255, 255, 100));
        g2.fillOval(positionX + size / 4, positionY + size / 6, size / 3, size / 3);

        g2.setColor(new Color(80, 40, 0));
        g2.fillRoundRect(positionX + size / 2 - 2, positionY - 4, 4, 6, 2, 2);

        g2.setColor(new Color(0, 180, 0));
        int[] leafX = { positionX + size / 2 + 1, positionX + size / 2 + 6, positionX + size / 2 + 2 };
        int[] leafY = { positionY - 3, positionY + 2, positionY + 4 };
        g2.fillPolygon(leafX, leafY, 3);

        g2.dispose();
    }
}
