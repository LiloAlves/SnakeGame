package com.game.snake.entities;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Point> body;
    private int velocityX, velocityY;
    private Color color;
    private boolean growing = false;

    public Snake(int startX, int startY, Color color) {
        this.body = new ArrayList<>();
        this.body.add(new Point(startX, startY));
        this.velocityX = 0;
        this.velocityY = 0;
        this.color = color;
    }

    public void move() {
        Point head = getHead();
        Point newHead = new Point(head.x + velocityX, head.y + velocityY);
        body.add(0, newHead);

        if (!growing) {
            body.remove(body.size() - 1);
        } else {
            growing = false;
        }
    }

    public void grow() {
        growing = true;
    }

    public boolean checkCollision(Point other) {
        for (Point part : body) {
            if (part.equals(other)) {
                return true;
            }
        }
        return false;
    }

    public Point getHead() {
        return body.get(0);
    }

    public void setDirection(int velocityX, int velocityY) {
        if ((this.velocityX != 0 && velocityX == -this.velocityX) ||
                (this.velocityY != 0 && velocityY == -this.velocityY)) {
            return;
        }
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void draw(Graphics g, int blockSize) {
        g.setColor(color);
        for (Point part : body) {
            g.fill3DRect(part.x * blockSize, part.y * blockSize, blockSize, blockSize, true);
        }
    }

    public boolean outOfBounds(int width, int height, int blockSize) {
        Point head = getHead();
        return head.x < 0 || head.x >= width / blockSize || head.y < 0 || head.y >= height / blockSize;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public ArrayList<Point> getBody() {
        return body;
    }

    public Color getColor() {
        return color;
    }
}
