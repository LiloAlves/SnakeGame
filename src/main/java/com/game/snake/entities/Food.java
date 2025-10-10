package com.game.snake.entities;

import java.awt.*;
import java.util.Random;

public class Food {
    private Point position;
    private Random random;

    public Food(int width, int height, int blockSize) {
        this.random = new Random();
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
        g.setColor(Color.RED);
        g.fill3DRect(position.x * blockSize, position.y * blockSize, blockSize, blockSize, true);
    }
}
