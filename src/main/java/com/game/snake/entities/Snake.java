package com.game.snake.entities;

import java.awt.*;
import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Snake {

    private ArrayList<Point> body;
    private int velocityX, velocityY;
    private Color color;
    private java.util.List<Color> colors;

    @Getter(AccessLevel.NONE)
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
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < body.size(); i++) {
            Point p = body.get(i);

            if (i == 0) {
                g2.setColor(color);
            } else {
                int alpha = Math.max(60, 200 - i * 10);
                Color faded = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.min(255, alpha));
                g2.setColor(faded);
            }

            g2.fillRoundRect(p.x * blockSize, p.y * blockSize, blockSize, blockSize, 8, 8);

            if (i == 0) {
                drawEyes(g2, p, blockSize);
            }

        }
    }

    private void drawEyes(Graphics2D g, Point head, int blockSize) {
        int headx = head.x * blockSize;
        int heady = head.y * blockSize;

        int eyeSize = Math.max(4, blockSize / 4);
        int pupilSize = Math.max(3, eyeSize / 2);
        int offset = Math.max(2, blockSize / 4);

        int leftX = headx + offset;
        int rightX = headx + blockSize - offset - eyeSize;
        int topY = heady + offset;
        int bottomY = heady + blockSize - offset - eyeSize;

        int leftEyeX, leftEyeY, rightEyeX, rightEyeY;
        int pupilOffsetX = 0, pupilOffsetY = 0;

        if (velocityX > 0) {
            leftEyeX = rightX;
            rightEyeX = rightX;
            leftEyeY = topY;
            rightEyeY = bottomY;
            pupilOffsetX = pupilSize / 3;
        } else if (velocityX < 0) {
            leftEyeX = leftX;
            rightEyeX = leftX;
            leftEyeY = topY;
            rightEyeY = bottomY;
            pupilOffsetX = -pupilSize / 3;
        } else if (velocityY > 0) {
            leftEyeX = leftX;
            rightEyeX = rightX;
            leftEyeY = bottomY;
            rightEyeY = bottomY;
            pupilOffsetY = pupilSize / 3;
        } else {
            leftEyeX = leftX;
            rightEyeX = rightX;
            leftEyeY = topY;
            rightEyeY = topY;
            pupilOffsetY = -pupilSize / 3;
        }

        g.setColor(new Color(255, 255, 255));
        g.fillOval(leftEyeX, leftEyeY, eyeSize, eyeSize);
        g.fillOval(rightEyeX, rightEyeY, eyeSize, eyeSize);

        g.setColor(Color.BLACK);
        g.fillOval(leftEyeX + eyeSize / 2 - pupilSize / 2 + pupilOffsetX,
                leftEyeY + eyeSize / 2 - pupilSize / 2 + pupilOffsetY,
                pupilSize, pupilSize);
        g.fillOval(rightEyeX + eyeSize / 2 - pupilSize / 2 + pupilOffsetX,
                rightEyeY + eyeSize / 2 - pupilSize / 2 + pupilOffsetY,
                pupilSize, pupilSize);
    }

    public boolean outOfBounds(int width, int height, int blockSize) {
        Point head = getHead();
        return head.x < 0 || head.x >= width / blockSize || head.y < 0 || head.y >= height / blockSize;
    }
}
